package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class PacketRewriterEncoder extends MessageToMessageEncoder<BetaRecordPacket> {
	private static final Logger LOGGER = LogManager.getLogger(PacketRewriterEncoder.class);
	public static final String KEY = "packet-rewriter-encoder";

	private final Connection connection;
	private final EncoderRewriter rewriter;

	public PacketRewriterEncoder(final Connection connection) {
		this.connection = connection;
		this.rewriter = new EncoderRewriter();
		this.rewriter.registerPackets();
	}

	// Server -> Proxy -> Client
	@Override
	protected void encode(final ChannelHandlerContext context, final BetaRecordPacket packet, final List<Object> out) throws Exception {
		for (final Class<?> clazz : this.rewriter.clientboundRewriters.keySet()) {
			if (clazz.isAssignableFrom(packet.getClass())) {
				final ModernRecordPacket<?> modernPacket = this.rewriter.clientboundRewriters.get(clazz).apply(this.connection, packet);
				if (modernPacket != null) {
					out.add(modernPacket);
				}

				return;
			}
		}

		LOGGER.warn("Skipping packet {} as it was not rewritten", packet.getType());
	}
}
