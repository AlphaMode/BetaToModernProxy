package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.networking.ProxyChannel;

public final class BetaPacketWriter extends MessageToByteEncoder<BetaRecordPacket> {
	public static final String KEY = "beta-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final BetaRecordPacket packet, final ByteBuf buf) throws Exception {
		final BetaPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.BETA_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new IllegalStateException("Can't write Packet without a packet registry");
		} else {
			final BetaPackets type = packet.getType();
			buf.writeByte(type.getId());
			try {
				packetRegistry.getCodec(type).encode(buf, packet);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
