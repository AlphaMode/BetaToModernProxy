package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;

public class ModernPacketWriter extends MessageToByteEncoder<ModernRecordPacket<ModernPackets>> {
	public static final String KEY = "modern-decoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final ByteBuf buf) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new IllegalStateException("Can't write Packet without a packet registry");
		} else {
			final ModernPackets type = packet.getType();
			ModernCodecs.VAR_INT.encode(buf, type.getId());
			try {
				packetRegistry.getCodec(type).encode(buf, packet);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
