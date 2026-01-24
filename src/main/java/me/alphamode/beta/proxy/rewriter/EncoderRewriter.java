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
			connection.send(connection.createKeepAlivePacket(lastMs));
		});

		this.registerClientboundRewriter(HandshakePacket.class, (_, _) -> new S2CHelloPacket("_", new byte[0], new byte[0], false));

		this.registerClientboundRewriter(LoginPacket.class, (_, _) -> {
		});

		this.registerClientboundRewriter(SetSpawnPositionPacket.class, (_, packet) -> {
		});

		// SET_SPAWN_POSITION

		// CHUNK_VISIBILITY

		// BLOCK_REGION_UPDATE

		// MOVE_PLAYER_POS_ROT

		// CHAT

		// CONTAINER_SET_CONTENT

		// CONTAINER_SET_SLOT

		this.registerClientboundRewriter(SetTimePacket.class, (connection, packet) -> {
			final long time = packet.time();
			connection.send(new S2CSetTimePacket(time, time, time > -1));
		});

		this.registerClientboundRewriter(DisconnectPacket.class, (connection, packet) -> {
			connection.kick(packet.reason());
		});
	}
}
