package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public final class BetaPacketWriter extends MessageToByteEncoder<BetaRecordPacket> {
	public static final String KEY = "beta-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final BetaRecordPacket packet, final ByteBuf buf) throws Exception {
		final BetaPackets type = packet.getType();
		buf.writeByte(type.getId());
		try {
			BetaPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
