package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<Object> {
	private static final Logger LOGGER = LogManager.getLogger(PacketRewriterDecoder.class);
	public static final String KEY = "packet-rewriter-decoder";

	private final DecoderRewriter rewriter;

	public PacketRewriterDecoder(final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		this.rewriter = new DecoderRewriter(defaultTags, defaultRegistries);
		this.rewriter.registerPackets();
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final Object packet, final List<Object> out) throws Exception {
		LOGGER.debug("Rewriter hit: {}", packet.getClass());
		final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
		for (final Class<?> clazz : this.rewriter.serverboundRewriters.keySet()) {
			if (clazz.isAssignableFrom(packet.getClass())) {
				final BetaRecordPacket betaPacket = this.rewriter.serverboundRewriters.get(clazz).apply(connection, (ModernRecordPacket<?>) packet);
				if (betaPacket != null) {
					out.add(betaPacket);
				}

				return;
			}
		}
	}
}
