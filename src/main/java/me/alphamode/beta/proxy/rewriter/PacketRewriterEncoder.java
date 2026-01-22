package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;

import java.util.List;

public final class PacketRewriterEncoder extends MessageToMessageEncoder<BetaRecordPacket> {
	private final EncoderRewriter rewriter = new EncoderRewriter();

	public PacketRewriterEncoder() {
		rewriter.registerPackets();
	}

	// S -> P
	@Override
	protected void encode(final ChannelHandlerContext context, final BetaRecordPacket packet, final List<Object> out) throws Exception {
		final BetaPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.BETA_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot encode beta packet as packet-registry is null!");
		} else {
			final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
			IO.println("sending beta packet to modern client");
			IO.println(packet);
			for (final Class<?> clazz : this.rewriter.clientboundRewriters.keySet()) {
				if (clazz.isAssignableFrom(packet.getClass())) {
					final ModernRecordPacket<?> modernPacket = this.rewriter.clientboundRewriters.get(clazz).apply(connection, packet);
					if (modernPacket != null) {
						out.add(modernPacket);
					}

					return;
				}
			}
		}
	}
}
