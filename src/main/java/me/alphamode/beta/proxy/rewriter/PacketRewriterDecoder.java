package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;

import java.util.List;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<ModernRecordPacket<ModernPackets>> {
	private final DecoderRewriter rewriter;

	public PacketRewriterDecoder(final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		this.rewriter = new DecoderRewriter(defaultTags, defaultRegistries);
		this.rewriter.registerPackets();
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final List<Object> out) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode modern packet as packet-registry is null!");
		} else {
			final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
//			Proxy.LOGGER.info((packet);
			for (final Class<?> clazz : this.rewriter.serverboundRewriters.keySet()) {
				if (clazz.isAssignableFrom(packet.getClass())) {
					final BetaRecordPacket betaPacket = this.rewriter.serverboundRewriters.get(clazz).apply(connection, packet);
					if (betaPacket != null) {
//						Proxy.LOGGER.info("writing beta packet");
//						Proxy.LOGGER.info(betaPacket);
						out.add(betaPacket);
					}

					return;
				}
			}
		}
	}
}
