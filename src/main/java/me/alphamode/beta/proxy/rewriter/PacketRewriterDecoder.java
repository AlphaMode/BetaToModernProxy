package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<ModernRecordPacket<? extends ModernPackets>> {
	private static final Logger LOGGER = LogManager.getLogger(PacketRewriterDecoder.class);
	public static final String KEY = "packet-rewriter-decoder";

	private final Connection connection;
	private final DecoderRewriter rewriter;

	public PacketRewriterDecoder(final Connection connection, final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		this.connection = connection;
		this.rewriter = new DecoderRewriter(defaultTags, defaultRegistries);
		this.rewriter.registerPackets();
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final ModernRecordPacket<? extends ModernPackets> packet, final List<Object> out) throws Exception {
		LOGGER.debug("Rewriter hit: {}", packet.getClass());
		for (final Class<?> clazz : this.rewriter.serverboundRewriters.keySet()) {
			if (clazz.isAssignableFrom(packet.getClass())) {
				final BetaRecordPacket betaPacket = this.rewriter.serverboundRewriters.get(clazz).apply(this.connection, packet);
				if (betaPacket != null) {
					out.add(betaPacket);
				} else {
					LOGGER.warn("Skipping packet {} as it was not rewritten", packet.getType());
				}

				return;
			}
		}
	}
}
