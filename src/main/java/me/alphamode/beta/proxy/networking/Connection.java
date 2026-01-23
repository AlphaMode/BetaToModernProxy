package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CDisconnectPacket;
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

import java.util.UUID;

// Proxy -> Client
public final class Connection extends SimpleChannelInboundHandler<Object> {
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

			BrodernProxy.LOGGER.info("Sending {} to client!", packet.getType());
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
				case PLAY -> this.send(new S2CDisconnectPacket(message));
				case STATUS -> throw new RuntimeException("Cannot send disconnect packet during STATUS state");
				case LOGIN -> throw new RuntimeException("TODO");
				case CONFIGURATION -> throw new RuntimeException("TODO");
			}
		}

		if (this.channel.isActive()) {
			this.disconnect();
		}
	}

	public void kick(final String message) {
		this.kick(TextComponent.of(message));
	}

	public void disconnect() {
		if (this.isConnected()) {
			this.channel.close();
			this.channel = null;
		}
	}

	public boolean isConnected() {
		return this.channel != null && this.channel.isActive();
	}

	public PacketState getState() {
		return this.state;
	}

	public void setState(final PacketState state) {
		BrodernProxy.LOGGER.info("Switching to state {}", state);
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
		BrodernProxy.LOGGER.info("Connection acquired!");
		this.channel = context.channel();
		if (this.realServer == null) {
			BrodernProxy.LOGGER.info("Proxy {} connected to {}", LAST_CONNECTION_ID++, this.serverAddress);
			this.realServer = new NetClient(new RelayChannel(context.channel()));
			this.realServer.connect(this.serverAddress).addListener(future -> {
				if (!future.isSuccess()) {
					BrodernProxy.LOGGER.info("Failed to connect to real server!");
					future.cause().printStackTrace();
					context.close();
				}
			});
			this.realServer.getChannel().pipeline().addLast(new SimpleChannelInboundHandler<>() {
				@Override
				protected void channelRead0(final ChannelHandlerContext ctx, final Object data) {
					BrodernProxy.LOGGER.info(data);
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
		BrodernProxy.LOGGER.info("Received Packet {}", packet);
		if (this.realServer != null && this.realServer.getChannel().isActive()) {
			this.realServer.getChannel().writeAndFlush(packet);
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		LAST_CONNECTION_ID--;
		BrodernProxy.LOGGER.info("Connection lost!");
		if (this.realServer != null) {
			BrodernProxy.LOGGER.info("Disconnected from real server!");
			this.realServer.getChannel().close();
			this.realServer = null;
		}
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		cause.printStackTrace();
		context.close();
	}
}
