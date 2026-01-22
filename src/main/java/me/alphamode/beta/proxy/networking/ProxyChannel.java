package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.rewriter.PacketRewriterDecoder;
import net.raphimc.netminecraft.netty.codec.PacketSizer;

// Packets
public final class ProxyChannel extends ChannelInitializer<Channel> {
	public static final AttributeKey<BetaPacketRegistry> BETA_PACKET_REGISTRY_KEY = AttributeKey.valueOf("beta_packet_registry");
	public static final AttributeKey<ModernPacketRegistry> MODERN_PACKET_REGISTRY_KEY = AttributeKey.valueOf("modern_packet_registry");
	private final String serverIp;

	public ProxyChannel(final String ip) {
		this.serverIp = ip;
	}

	// Client -> Proxy
	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(MODERN_PACKET_REGISTRY_KEY).set(ModernPacketRegistry.INSTANCE);

		channel.pipeline().addLast(new PacketSizer());
		channel.pipeline().addLast(new ModernPacketDecoder());

		final C2PChannel clientChannel = new C2PChannel(this.serverIp);
		channel.pipeline().addLast(new PacketRewriterDecoder(clientChannel));
		channel.pipeline().addLast(clientChannel);
	}
}
