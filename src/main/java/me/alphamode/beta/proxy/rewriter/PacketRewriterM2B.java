package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

// Client -> Proxy
public final class PacketRewriterM2B extends MessageToMessageEncoder<ModernRecordPacket<? extends ModernPackets>> {
	private static final Logger LOGGER = LogManager.getLogger(PacketRewriterM2B.class);
	public static final String KEY = "packet-rewriter-decoder";

	private final Connection connection;
	private final DecoderRewriter rewriter;

	public PacketRewriterM2B(final Connection connection) {
		this.connection = connection;
		this.rewriter = new DecoderRewriter();
		this.rewriter.registerPackets();
	}

	// P -> C
	@Override
	protected void encode(final ChannelHandlerContext context, final ModernRecordPacket<? extends ModernPackets> packet, final List<Object> out) throws Exception {
		LOGGER.warn("Encoding Modern to Beta Packet ({})", packet.getType());
		for (final Class<?> clazz : this.rewriter.serverboundRewriters.keySet()) {
			if (clazz.isAssignableFrom(packet.getClass())) {
				final BetaRecordPacket betaPacket = this.rewriter.serverboundRewriters.get(clazz).apply(this.connection, packet);
				if (betaPacket != null) {
					out.add(betaPacket);
				}

				return;
			}
		}

		LOGGER.warn("Skipping packet {} as it was not rewritten", packet.getType());
	}
}
