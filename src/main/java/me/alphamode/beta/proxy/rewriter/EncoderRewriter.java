package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CSetTimePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EncoderRewriter extends Rewriter {
	private static final Logger LOGGER = LogManager.getLogger(EncoderRewriter.class);

	@Override
	public void registerPackets() {
		this.registerClientboundRewriter(KeepAlivePacket.class, (connection, _) -> {
			final long lastMs = connection.getLastKeepAliveMS();
			connection.setLastKeepAliveMS(System.currentTimeMillis());
			return connection.createKeepAlivePacket(lastMs);
		});

		this.registerClientboundRewriter(HandshakePacket.class, (_, _) -> {
			LOGGER.info("Encoding Handshake Packet to Modern");
			return new S2CHelloPacket("_", new byte[0], new byte[0], false);
		});

		this.registerClientboundRewriter(LoginPacket.class, (_, _) -> {
			LOGGER.info("Encoding Login Packet to Modern");
			return null;
		});

		this.registerClientboundRewriter(SetSpawnPositionPacket.class, (_, packet) -> {
			return null;
		});

		// SET_SPAWN_POSITION

		// CHUNK_VISIBILITY

		// BLOCK_REGION_UPDATE

		// MOVE_PLAYER_POS_ROT

		// CHAT

		// CONTAINER_SET_CONTENT

		// CONTAINER_SET_SLOT

		this.registerClientboundRewriter(SetTimePacket.class, (_, packet) -> {
			final long time = packet.time();
			return new S2CSetTimePacket(time, time, time > -1);
		});

		this.registerClientboundRewriter(DisconnectPacket.class, (connection, packet) -> {
			connection.kick(packet.reason());
			return null;
		});
	}
}
