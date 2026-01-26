package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// TODO: move to BetaPacketCodec
public final class BetaPacketEncoder extends MessageToByteEncoder<BetaPacket> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketEncoder.class);
	public static final String KEY = "beta-packet-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final BetaPacket packet, final ByteBuf buf) throws Exception {
		final BetaPackets type = packet.getType();
		buf.writeByte(type.getId());
		try {
			BetaPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			LOGGER.info("Failed to encode beta packet");
			throw new RuntimeException(exception);
		}
	}
}
