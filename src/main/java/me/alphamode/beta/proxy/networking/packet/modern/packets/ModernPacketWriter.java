package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;

public class ModernPacketWriter extends MessageToByteEncoder<ModernRecordPacket<ModernPackets>> {
	public static final String KEY = "modern-decoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final ByteBuf buf) throws Exception {
		final ModernPackets type = packet.getType();
		ModernStreamCodecs.VAR_INT.encode(buf, type.getId());
		try {
			ModernPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
}
