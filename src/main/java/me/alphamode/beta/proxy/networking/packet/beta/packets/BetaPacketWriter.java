package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BetaPacketWriter extends MessageToByteEncoder<BetaPacket> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketWriter.class);
	public static final String KEY = "beta-packet-writer";

	@Override
	protected void encode(final ChannelHandlerContext context, final BetaPacket packet, final ByteBuf buf) throws Exception {
		final BetaPackets type = packet.getType();
		buf.writeByte(type.getId());
		try {
			BetaPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			LOGGER.info("Failed to encode beta packet: {}", exception.getMessage());
			throw new RuntimeException(exception);
		}
	}
}
