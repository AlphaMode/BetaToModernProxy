package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayDisconnectPacket;
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

// Proxy -> Client
public final class Connection extends SimpleChannelInboundHandler<Object> {
	private static final Logger LOGGER = LogManager.getLogger(Connection.class);
	private static int LAST_CONNECTION_ID = 0;

	private final MinecraftServerAddress serverAddress;
	private NetClient realServer;
	private Channel channel;
	private PacketState state = PacketState.HANDSHAKING;
	private UUID uuid;
	private String username;
	private int protocolVersion = BetaRecordPacket.PROTOCOL_VERSION; // Assume Beta?

	public Connection(final MinecraftServerAddress serverAddress) {
		this.serverAddress = serverAddress;
	}

	public void write(final ByteBuf buf) {
		if (this.isConnected()) {
			this.channel.writeAndFlush(buf);
		} else {
			throw new RuntimeException("Cannot write to dead connection!");
		}
	}

	public void send(final RecordPacket<?> packet) {
		if (this.isConnected()) {
			if (packet instanceof ModernRecordPacket<?> modernPacket && modernPacket.getState() != this.state) {
				throw new RuntimeException("Cannot write packet in state " + this.state + " as it does not match the packet's state " + modernPacket.getState());
			}

			LOGGER.info("Sending {} to client!", packet.getType());
			this.channel.writeAndFlush(packet);
		} else {
			throw new RuntimeException("Cannot write to dead connection!");
		}
	}

	public void kick(final TextComponent message) {
		if (this.protocolVersion == BetaRecordPacket.PROTOCOL_VERSION) {
			this.send(new DisconnectPacket(message.asLegacyFormatString()));
		} else {
			switch (this.state) {
				case HANDSHAKING ->
						throw new RuntimeException("Cannot send disconnect packet during HANDSHAKING state");
				case PLAY -> this.send(new S2CPlayDisconnectPacket(message));
				case STATUS -> throw new RuntimeException("Cannot send disconnect packet during STATUS state");
				case LOGIN -> throw new RuntimeException("TODO LOGIN DISCONNECT");
				case CONFIGURATION -> throw new RuntimeException("TODO CONFIGURATION DISCONNECT");
			}
		}

		this.disconnect();
	}

	public void kick(final String message) {
		this.kick(TextComponent.of(message));
	}

	public void disconnect() {
		if (this.isConnected()) {
			this.channel.closeFuture();
			this.channel = null;
		}
	}

	public boolean isConnected() {
		return this.channel != null;
	}

	public PacketState getState() {
		return this.state;
	}

	public void setState(final PacketState state) {
		LOGGER.info("Switching to state {}", state);
		this.state = state;
	}

	public UUID getId() {
		return this.uuid;
	}

	public void setId(final UUID uuid) {
		this.uuid = uuid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public int getProtocolVersion() {
		return this.protocolVersion;
	}

	public void setProtocolVersion(final int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	@Override
	public void channelActive(final ChannelHandlerContext context) {
		LOGGER.info("Connection acquired!");
		this.channel = context.channel();
		if (this.realServer == null) {
			LOGGER.info("Proxy {} connected to {}", LAST_CONNECTION_ID++, this.serverAddress);
			this.realServer = new NetClient(new RelayChannel(context.channel()));
			this.realServer.connect(this.serverAddress).addListener(future -> {
				if (!future.isSuccess()) {
					LOGGER.info("Failed to connect to real server!");
					future.cause().printStackTrace();
					context.close();
				}
			});
			this.realServer.getChannel().pipeline().addLast(new SimpleChannelInboundHandler<>() {
				@Override
				protected void channelRead0(final ChannelHandlerContext ctx, final Object data) {
					LOGGER.info(data);
					channel.writeAndFlush(data);
				}

				@Override
				public void channelInactive(final ChannelHandlerContext ctx) {
					context.close();
				}
			});
		}
	}

	// Out channel (Writing from Proxy to Serer)
	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final Object packet) {
		LOGGER.info("Sending Packet to Server: {}", packet);
		if (this.realServer != null) {
			this.realServer.getChannel().writeAndFlush(packet).syncUninterruptibly();
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		LAST_CONNECTION_ID--;
		LOGGER.info("Connection lost!");
		if (this.realServer != null) {
			LOGGER.info("Disconnected from real server!");
			this.realServer.getChannel().closeFuture();
			this.realServer = null;
		}
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		LOGGER.error(String.format("Caught exception in connection (%s)", this.username), cause);
        this.kick(cause.getMessage());
	}
}
