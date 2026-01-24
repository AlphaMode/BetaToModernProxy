package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.PacketHandler;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
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
import net.lenni0451.mcstructs.text.TextComponent;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

// Proxy -> Client
public final class Connection extends SimpleChannelInboundHandler<ByteBuf> implements PacketHandler {
	private static final Logger LOGGER = LogManager.getLogger(Connection.class);
	private static int LAST_CONNECTION_ID = 0;

	private final MinecraftServerAddress serverAddress;
	private Channel serverChannel;
	private Channel clientChannel;
	private PacketState state = PacketState.HANDSHAKING;
	private UUID uuid;
	private String username;
	private int protocolVersion = BetaRecordPacket.PROTOCOL_VERSION; // Assume Beta?
	private long lastKeepAliveMS = 0L;

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
		LOGGER.error("Kicking client with reason: {}", message);
		this.kick(TextComponent.of(message));
	}

	public void disconnect() {
		LAST_CONNECTION_ID--;
		if (this.serverChannel != null) {
			LOGGER.info("Disconnected from real server!");
			this.serverChannel.close();
			this.serverChannel = null;
		}

		if (this.clientChannel != null) {
			LOGGER.info("Disconnected proxy {}!", LAST_CONNECTION_ID);
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
		// Inbound
		// Outbound -> ModernRecordPacket<T> -> Write Modern Packets
		this.clientChannel = context.channel();
		this.clientChannel.pipeline().addLast(ModernPacketWriter.KEY, new ModernPacketWriter());

		LOGGER.info("Proxy {} connected to {}", LAST_CONNECTION_ID++, this.serverAddress);

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

			LOGGER.info("Connected to real server!");
			this.serverChannel = realServerConnection.getChannel();
		});
	}

	// Out channel (Writing from Proxy to Serer)
	@Override
	protected void channelRead0(final ChannelHandlerContext context, final ByteBuf buf) {
		LOGGER.info("Sending Packet to Server: {}", buf);
		if (this.isConnectedToServer()) {
			this.serverChannel.writeAndFlush(buf.retain());
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		this.disconnect();
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		LOGGER.error("Caught exception in connection ({})", this.username, cause);
		this.kick(cause.getMessage());
	}
}
