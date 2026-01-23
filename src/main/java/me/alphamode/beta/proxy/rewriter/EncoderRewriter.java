package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CHelloPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class EncoderRewriter extends Rewriter {
	private static final Logger LOGGER = LogManager.getLogger(EncoderRewriter.class);

	@Override
	public void registerPackets() {
		this.registerClientboundRewriter(HandshakePacket.class, (connection, packet) -> {
			LOGGER.info("Encoding Handshake Packet to Modern");
			return new S2CHelloPacket("_", new byte[0], new byte[0], false);
		});

		this.registerClientboundRewriter(LoginPacket.class, (connection, packet) -> {
			LOGGER.info("Encoding Login Packet to Modern");
			return null;
		});
	}
}
