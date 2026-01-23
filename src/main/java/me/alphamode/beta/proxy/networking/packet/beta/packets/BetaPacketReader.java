package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public final class BetaPacketReader extends ReplayingDecoder<Void> {
	public static final String KEY = "beta-decoder";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) {
		try {
			out.add(BetaPacketRegistry.INSTANCE.createPacket(buf.readUnsignedByte(), null /* Unused */, null /* unused */, buf));
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
