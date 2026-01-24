package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.PacketHandler;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayKeepAlivePacket;
import me.alphamode.beta.proxy.rewriter.DecoderRewriter;
import me.alphamode.beta.proxy.rewriter.EncoderRewriter;
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

public final class Connection extends SimpleChannelInboundHandler<ModernRecordPacket<?>> implements PacketHandler {
	private static final Logger LOGGER = LogManager.getLogger(Connection.class);
	private static int LAST_CONNECTION_ID = 0;

	private final MinecraftServerAddress serverAddress;
	private final DecoderRewriter decoderRewriter = new DecoderRewriter();
	private final EncoderRewriter encoderRewriter = new EncoderRewriter();
	private final int id;
	private Channel serverChannel;
	private Channel clientChannel;
	private PacketState state = PacketState.HANDSHAKING;
	private UUID uuid;
	private String username;
	private int protocolVersion = BetaRecordPacket.PROTOCOL_VERSION; // Assume Beta?
	private long lastKeepAliveMS = 0L;

	public Connection(final MinecraftServerAddress serverAddress) {
		this.serverAddress = serverAddress;
		this.decoderRewriter.registerPackets();
		this.encoderRewriter.registerPackets();
		this.id = LAST_CONNECTION_ID++;
	}

	public DecoderRewriter getDecoderRewriter() {
		return this.decoderRewriter;
	}

	public EncoderRewriter getEncoderRewriter() {
		return this.encoderRewriter;
	}

	public void write(final ByteBuf buf, final boolean serverbound) {
		if (this.isConnectedToServer() && serverbound) {
			this.serverChannel.writeAndFlush(buf);
		} else if (this.isConnected() && !serverbound) {
			this.clientChannel.writeAndFlush(buf);
		} else {
			throw new RuntimeException("Cannot write to dead " + (serverbound ? "server" : "client") + " connection!");
		}
	}

	public void sendToServer(final BetaRecordPacket packet) {
		if (this.isConnectedToServer()) {
			this.serverChannel.writeAndFlush(packet);
		} else {
			throw new RuntimeException("Cannot write to dead server connection!");
		}
	}

	public void sendToClient(final ModernRecordPacket<?> packet) {
		if (this.isConnected()) {
			if (packet.getState() != this.state) {
				throw new RuntimeException("Cannot write packet in state " + this.state + " as it does not match the packet's state " + packet.getState() + " for packet " + packet);
			}

			this.clientChannel.writeAndFlush(packet);
		} else {
			throw new RuntimeException("Cannot write to dead client connection!");
		}
	}

	public void kick(final TextComponent message) {
		LOGGER.error("Kicking client with reason: {}", message.toString());
		if (this.protocolVersion == BetaRecordPacket.PROTOCOL_VERSION) {
			this.sendToServer(new DisconnectPacket(message.asLegacyFormatString()));
		} else {
			final S2CCommonDisconnectPacket<?> disconnectPacket = this.createDisconnectPacket(message);
			if (disconnectPacket != null) {
				this.sendToClient(disconnectPacket);
			}
		}

		this.disconnect();
	}

	public void kick(final String message) {
		this.kick(TextComponent.of(message));
	}

	public void disconnect() {
		LAST_CONNECTION_ID--;
		if (this.serverChannel != null) {
			LOGGER.info("Disconnected Proxy #{} from real server!", this.id);
			this.serverChannel.close();
			this.serverChannel = null;
		}

		if (this.clientChannel != null) {
			LOGGER.info("Disconnected Proxy #{}!", this.id);
			this.clientChannel.close();
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
		LOGGER.info("Switching Proxy #{} to state {}", this.id, state);
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

	public long getLastKeepAliveMS() {
		return this.lastKeepAliveMS;
	}

	public void setLastKeepAliveMS(final long lastKeepAliveMS) {
		this.lastKeepAliveMS = lastKeepAliveMS;
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
		this.clientChannel = context.channel();

		final NetClient realServerConnection = new NetClient(new Proxy2ClientChannelInit(this));
		realServerConnection.connect(this.serverAddress).addListener(future -> {
			if (!future.isSuccess()) {
				LOGGER.info("Failed to connect to real server!");
				future.cause().printStackTrace();
				this.disconnect();
				return;
			}

			if (!this.isConnected()) {
				LOGGER.info("Client already has disconnected, closing the server connection!");
				realServerConnection.getChannel().close();
				return;
			}

			LOGGER.info("Proxy #{} connected to {}", this.id, this.serverAddress);
			this.serverChannel = realServerConnection.getChannel();
		}).syncUninterruptibly();
	}

	// Out channel (Writing from Proxy to Serer)
	@Override
	protected void channelRead0(final ChannelHandlerContext context, final ModernRecordPacket<?> packet) {
		if (this.isConnectedToServer()) {
			this.decoderRewriter.rewrite(this, packet);
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		LOGGER.warn("Proxy #{} Became Inactive, Disconnecting...", this.id);
		this.disconnect();
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		LOGGER.error("Caught exception in Proxy #{} ({})", this.id, this.username, cause);
	}
}
