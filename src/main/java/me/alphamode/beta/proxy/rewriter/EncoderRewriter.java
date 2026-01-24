package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CHelloPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EncoderRewriter extends Rewriter<BetaRecordPacket> {
	private static final Logger LOGGER = LogManager.getLogger(EncoderRewriter.class);

	@Override
	public void registerPackets() {
		this.registerClientboundRewriter(KeepAlivePacket.class, (connection, _) -> {
			final long lastMs = connection.getLastKeepAliveMS();
			connection.setLastKeepAliveMS(System.currentTimeMillis());
			connection.sendToClient(connection.createKeepAlivePacket(lastMs));
		});

		this.registerClientboundRewriter(HandshakePacket.class, (connection, packet) -> {
            connection.sendToClient(new S2CHelloPacket("", new byte[0], new byte[0], false));
            if (packet.username().equals("-")) {
                connection.sendToServer(new LoginPacket(BetaRecordPacket.PROTOCOL_VERSION, connection.getUsername()));
            } else {
                connection.kick("Online mode isn't supported!");
                return;
            }
            connection.sendToClient(new S2CHelloPacket("", new byte[0], new byte[0], false));
        });

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

//		this.registerClientboundRewriter(SetTimePacket.class, (connection, packet) -> {
//			final long time = packet.time();
//			connection.sendToClient(new S2CSetTimePacket(time, time, time > -1));
//		});

		this.registerClientboundRewriter(DisconnectPacket.class, (connection, packet) -> {
			connection.kick(packet.reason());
		});
	}

	@Override
	public void rewrite(final Connection connection, final BetaRecordPacket packet) {
//		LOGGER.warn("Encoding Beta to Modern Packet ({})", packet.getType());
		final RewriterFactory<BetaRecordPacket> rewriter = this.getClientboundRewriter(packet.getClass());
		if (rewriter != null) {
			rewriter.rewrite(connection, packet);
		}
	}
}
