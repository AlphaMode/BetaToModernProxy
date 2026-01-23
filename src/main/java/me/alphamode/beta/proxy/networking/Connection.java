package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.PacketHandler;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketWriter;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayKeepAlivePacket;
import me.alphamode.beta.proxy.rewriter.PacketRewriterEncoder;
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

// Proxy -> Client
public final class Connection extends SimpleChannelInboundHandler<Object> implements PacketHandler {
	private static final Logger LOGGER = LogManager.getLogger(Connection.class);
	private static int LAST_CONNECTION_ID = 0;

	private final MinecraftServerAddress serverAddress;
	private Channel serverChannel;
	private Channel clientChannel;
	private PacketState state = PacketState.HANDSHAKING;
	private UUID uuid;
	private String username;
	private int protocolVersion = BetaRecordPacket.PROTOCOL_VERSION; // Assume Beta?

	public Connection(final MinecraftServerAddress serverAddress) {
		this.serverAddress = serverAddress;
	}

	public void write(final ByteBuf buf) {
		if (this.isConnected() && this.clientChannel.isWritable()) {
			this.clientChannel.writeAndFlush(buf);
		} else {
			throw new RuntimeException("Cannot write to connection!");
		}
	}

	public void send(final RecordPacket<?> packet) {
		if (this.isConnected()) {
			if (packet instanceof ModernRecordPacket<?> modernPacket && modernPacket.getState() != this.state) {
				throw new RuntimeException("Cannot write packet in state " + this.state + " as it does not match the packet's state " + modernPacket.getState());
			}

			this.clientChannel.writeAndFlush(packet);
		} else {
			throw new RuntimeException("Cannot write to dead connection!");
		}
	}

	public void kick(final TextComponent message) {
		if (this.protocolVersion == BetaRecordPacket.PROTOCOL_VERSION) {
			this.send(new DisconnectPacket(message.asLegacyFormatString()));
		} else {
			final S2CCommonDisconnectPacket<?> disconnectPacket = this.createDisconnectPacket(message);
			if (disconnectPacket != null) {
				this.send(disconnectPacket);
			}
		}

		this.disconnect();
	}

	public void kick(final String message) {
		this.kick(TextComponent.of(message));
	}

	public void disconnect() {
		if (this.isConnectedToServer()) {
			this.serverChannel.closeFuture();
			this.serverChannel = null;
		}

		if (this.isConnected()) {
			this.clientChannel.closeFuture();
			this.clientChannel = null;
		}
	}

	public boolean isConnected() {
		return this.clientChannel != null && this.clientChannel.isActive();
	}

	public boolean isConnectedToServer() {
		return this.serverChannel != null && this.serverChannel.isActive();
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
	public S2CCommonDisconnectPacket<?> createDisconnectPacket(final TextComponent message) {
		return switch (this.state) {
			case HANDSHAKING, STATUS -> null;
			case PLAY -> new S2CPlayDisconnectPacket(message);
			case LOGIN -> new S2CLoginDisconnectPacket(message);
			case CONFIGURATION -> new S2CConfigurationDisconnectPacket(message);
		};
	}

	@Override
	public S2CCommonKeepAlivePacket<?> createKeepAlivePacket(final long time) {
		return switch (this.state) {
			case HANDSHAKING, STATUS, LOGIN -> null;
			case PLAY -> new S2CPlayKeepAlivePacket(time);
			case CONFIGURATION -> new S2CConfigurationKeepAlivePacket(time);
		};
	}

	@Override
	public void channelActive(final ChannelHandlerContext context) {
		LOGGER.info("Connection acquired!");

		this.clientChannel = context.channel();
		this.clientChannel.pipeline().addLast(ModernPacketWriter.KEY, new ModernPacketWriter());

		if (this.serverChannel == null) {
			LOGGER.info("Proxy {} connected to {}", LAST_CONNECTION_ID++, this.serverAddress);

			final NetClient realServerConnection = new NetClient(new RelayChannel(this));
			realServerConnection.connect(this.serverAddress).addListener(future -> {
				if (!future.isSuccess()) {
					LOGGER.info("Failed to connect to real server!");
					future.cause().printStackTrace();
					context.channel().closeFuture();
				}
			});

			this.serverChannel = realServerConnection.getChannel();
			this.serverChannel.pipeline().addLast(new SimpleChannelInboundHandler<>() {
				@Override
				protected void channelRead0(final ChannelHandlerContext ctx, final Object data) {
					LOGGER.info(data);
					clientChannel.writeAndFlush(data).syncUninterruptibly();
				}

				@Override
				public void channelInactive(final ChannelHandlerContext ctx) {
					context.close();
				}
			});

			final ChannelPipeline serverPipeline = this.serverChannel.pipeline();
			serverPipeline.addLast(PacketRewriterEncoder.KEY, new PacketRewriterEncoder(this));
			serverPipeline.addLast(BetaPacketWriter.KEY, new BetaPacketWriter());
		}
	}

	// Out channel (Writing from Proxy to Serer)
	@Override
	protected void channelRead0(final ChannelHandlerContext context, final Object object) {
		LOGGER.info("Sending Packet to Server: {}", object);
		if (this.isConnectedToServer()) {
			this.serverChannel.writeAndFlush(io.netty.util.ReferenceCountUtil.retain(object));
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		LAST_CONNECTION_ID--;
		LOGGER.info("Connection lost!");
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		LOGGER.error("Caught exception in connection ({})", this.username, cause);
		this.kick(cause.getMessage());
	}
}
