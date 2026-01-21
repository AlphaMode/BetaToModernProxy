package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import me.alphamode.beta.proxy.Proxy;
import me.alphamode.beta.proxy.networking.packet.beta.packets.RecordPacket;

import java.util.List;

public final class BetaPacketDecoder extends ReplayingDecoder<Void> {
	public static final String KEY = "beta-decoder";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) {
		final BetaPacketRegistry packetRegistry = context.channel().attr(Proxy.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode beta packet as packet-registry is null!");
		} else {
			try {
				final RecordPacket packet = packetRegistry.createPacket(buf.readUnsignedByte(), buf);
				IO.println("Decoding packet: " + packet);
				out.add(packet);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
}
