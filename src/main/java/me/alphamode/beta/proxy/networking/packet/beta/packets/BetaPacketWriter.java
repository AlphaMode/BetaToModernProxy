package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class BetaPacketWriter extends MessageToMessageDecoder<BetaRecordPacket> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketWriter.class);
	public static final String KEY = "beta-packet-writer";

	@Override
	protected void decode(final ChannelHandlerContext context, final BetaRecordPacket packet, final List<Object> out) throws Exception {
		LOGGER.info("Writing Beta Packet {}", packet);
		final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
		final BetaPackets type = packet.getType();
		buf.writeByte(type.getId());
		try {
			BetaPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			LOGGER.info("Failed to encode beta packet");
			throw new RuntimeException(exception);
		}
		out.add(buf);
	}
}
