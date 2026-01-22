package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.rewriter.PacketRewriterDecoder;
import net.raphimc.netminecraft.netty.codec.PacketSizer;

// Packets
public final class ProxyChannel extends ChannelInitializer<Channel> {
	public static final AttributeKey<BetaPacketRegistry> BETA_PACKET_REGISTRY_KEY = AttributeKey.newInstance("beta_packet_registry");
	public static final AttributeKey<ModernPacketRegistry> MODERN_PACKET_REGISTRY_KEY = AttributeKey.newInstance("modern_packet_registry");
	public static final AttributeKey<Connection> CONNECTION_KEY = AttributeKey.newInstance("connection");
	private final String serverIp;

	public ProxyChannel(final String ip) {
		this.serverIp = ip;
	}

	// Client -> Proxy
	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(MODERN_PACKET_REGISTRY_KEY).set(ModernPacketRegistry.INSTANCE);

		channel.pipeline().addLast(new PacketSizer());
		channel.pipeline().addLast(ModernPacketDecoder.KEY, new ModernPacketDecoder());
		channel.pipeline().addLast(ModernPacketEncoder.KEY, new ModernPacketEncoder());

		final Connection connection = new Connection(this.serverIp);
		channel.pipeline().addLast(new PacketRewriterDecoder());
		channel.pipeline().addLast(connection);
		channel.attr(CONNECTION_KEY).set(connection);
	}
}
