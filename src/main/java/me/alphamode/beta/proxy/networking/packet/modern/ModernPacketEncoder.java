package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;

public class ModernPacketEncoder extends MessageToByteEncoder<RecordPacket<ModernPackets>> {
	public static final String KEY = "modern-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final RecordPacket<ModernPackets> packet, final ByteBuf buf) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new IllegalStateException("Can't write Packet without a packet registry");
		} else {
			final ModernPackets type = packet.getType();

			final ByteBuf newBuf = Unpooled.buffer();
			try {
				ModernCodecs.VAR_INT.encode(newBuf, type.getId());
				packetRegistry.getCodec(type).encode(newBuf, packet);
			} catch (Exception exception) {
				exception.printStackTrace();
				return;
			}

			ModernCodecs.VAR_INT.encode(buf, newBuf.readableBytes());
			buf.writeBytes(newBuf);
		}
	}
}
