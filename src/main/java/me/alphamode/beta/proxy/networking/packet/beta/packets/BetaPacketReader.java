package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class BetaPacketReader extends ReplayingDecoder<Void> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketReader.class);
	public static final String KEY = "beta-packet-reader";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) {
		LOGGER.info("Reading Beta Packet");
		try {
			out.add(BetaPacketRegistry.INSTANCE.createPacket(buf.readUnsignedByte(), null /* Unused */, null /* unused */, buf));
		} catch (Exception exception) {
			LOGGER.info("Failed to decode beta packet");
			throw new RuntimeException(exception);
		}
	}
}
