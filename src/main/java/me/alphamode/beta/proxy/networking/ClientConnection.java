package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.PacketHandler;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.login.LoginPipeline;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ClientConnection extends SimpleChannelInboundHandler<ModernPacket<?>> implements PacketHandler {
	private static final Logger LOGGER = LogManager.getLogger(ClientConnection.class);
	private static int LAST_CONNECTION_ID = 0;

	private final MinecraftServerAddress address;
	private final int id;
	private ActivePipeline<?> pipeline = new ActivePipeline<>(LoginPipeline.PIPELINE, new LoginPipeline());
	private ServerConnection serverConnection;
	private Channel clientChannel;
	private PacketState state = PacketState.HANDSHAKING;
	private GameProfile profile;
	private int protocolVersion = BetaPacket.PROTOCOL_VERSION; // Assume Beta?
	private long lastKeepAliveMS = 0L;

	public ClientConnection(final MinecraftServerAddress address) {
		this.address = address;
		this.id = LAST_CONNECTION_ID++;
	}

	public void write(final ByteBuf buf) {
		if (this.isConnected()) {
			this.clientChannel.writeAndFlush(buf);
		} else {
			throw new RuntimeException("Cannot write to dead client connection!");
		}
	}

	public void send(final ModernPacket<?> packet) {
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

		final S2CCommonDisconnectPacket<?> disconnectPacket = this.createDisconnectPacket(message);
		if (disconnectPacket != null) {
			this.send(disconnectPacket);
		}

		this.disconnect();
	}

	public void kick(final String message) {
		this.kick(TextComponent.of(message));
	}

	public void disconnect() {
		if (this.serverConnection.isConnected()) {
			this.serverConnection.disconnect();
		}

		if (this.clientChannel != null) {
			LAST_CONNECTION_ID--;
			LOGGER.info("Disconnected Proxy #{}!", this.id);
			this.clientChannel.close().syncUninterruptibly();
			this.clientChannel = null;
		}
	}

	public boolean isConnected() {
		return this.clientChannel != null && this.clientChannel.isActive();
	}

	public int getId() {
		return this.id;
	}

	public ActivePipeline<?> getActivePipeline() {
		return this.pipeline;
	}

	public <H> void setPipeline(final PacketPipeline<H, BetaPacket, ModernPacket<?>> pipeline, final H handler) {
		this.pipeline = new ActivePipeline<>(pipeline, handler);
	}

	public ServerConnection getServerConnection() {
		return serverConnection;
	}

	public PacketState getState() {
		return this.state;
	}

	public void setState(final PacketState state) {
		LOGGER.info("Switching Proxy #{} to state {}", this.id, state);
		this.state = state;
	}

	public GameProfile getProfile() {
		return this.profile;
	}

	public void setProfile(final GameProfile profile) {
		this.profile = profile;
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

	public void tick() {
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
		this.serverConnection = new ServerConnection(this.address, this);
	}

	// Out channel (Writing from Proxy to Serer)
	@Override
	protected void channelRead0(final ChannelHandlerContext context, final ModernPacket<?> packet) {
		if (this.isConnected()) {
			this.pipeline.handleClient(this, packet);
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		this.disconnect();
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		LOGGER.error("Caught exception in Proxy #{} ({})", this.id, this.profile.name(), cause);
	}

	public record ActivePipeline<H>(PacketPipeline<H, BetaPacket, ModernPacket<?>> pipeline, H handler) {
		public void handleClient(final ClientConnection connection, final ModernPacket<?> packet) {
			pipeline.handleClient(handler, connection, packet);
		}

		public void handleServer(final ClientConnection connection, final BetaPacket packet) {
			pipeline.handleServer(handler, connection, packet);
		}
	}
}
