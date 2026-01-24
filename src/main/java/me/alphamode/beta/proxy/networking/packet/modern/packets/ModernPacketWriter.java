package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;

import java.util.List;

public class ModernPacketWriter extends MessageToMessageEncoder<ModernRecordPacket<ModernPackets>> {
	public static final String KEY = "modern-decoder";

	@Override
	protected void encode(final ChannelHandlerContext ctx, final ModernRecordPacket<ModernPackets> packet, final List<Object> out) throws Exception {
		final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
		final ModernPackets type = packet.getType();
		ModernStreamCodecs.VAR_INT.encode(buf, type.getId());
		try {
			ModernPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		out.add(buf);
	}
}
