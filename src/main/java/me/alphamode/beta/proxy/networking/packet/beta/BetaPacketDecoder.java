package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import me.alphamode.beta.proxy.networking.ProxyChannel;

import java.util.List;

public final class BetaPacketDecoder extends ReplayingDecoder<Void> {
	public static final String KEY = "beta-decoder";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) {
		final BetaPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode beta packet as packet-registry is null!");
		} else {
			try {
				out.add(packetRegistry.createPacket(BetaPackets.getPacket(buf.readUnsignedByte()), buf));
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
