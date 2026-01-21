package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.rewriter.PacketRewriter;

public final class ProxyChannel extends ChannelInitializer<Channel> {
	public static final AttributeKey<BetaPacketRegistry> PACKET_REGISTRY_ATTRIBUTE_KEY = AttributeKey.valueOf("beta_packet_registry");
	private final String serverIp;

	public ProxyChannel(final String ip) {
		this.serverIp = ip;
	}

	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(PACKET_REGISTRY_ATTRIBUTE_KEY).set(BetaPacketRegistry.INSTANCE);
		channel.pipeline().addLast(BetaPacketEncoder.KEY, new BetaPacketEncoder());
		channel.pipeline().addLast(BetaPacketDecoder.KEY, new BetaPacketDecoder());
		channel.pipeline().addLast(new C2PChannel(this.serverIp));

		channel.pipeline().addLast(new PacketRewriter(PacketDirection.CLIENTBOUND));
	}
}
