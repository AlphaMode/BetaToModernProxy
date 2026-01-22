package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketRegistry;
import me.alphamode.beta.proxy.rewriter.PacketRewriterDecoder;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.raphimc.netminecraft.netty.codec.PacketSizer;

// Packets
public final class ProxyChannel extends ChannelInitializer<Channel> {
	public static final AttributeKey<BetaPacketRegistry> BETA_PACKET_REGISTRY_KEY = AttributeKey.newInstance("beta_packet_registry");
	public static final AttributeKey<ModernPacketRegistry> MODERN_PACKET_REGISTRY_KEY = AttributeKey.newInstance("modern_packet_registry");
	public static final AttributeKey<Connection> CONNECTION_KEY = AttributeKey.newInstance("connection");
	private final String serverIp;
	private final CompoundTag defaultTags;
	private final CompoundTag defaultRegistries;

	public ProxyChannel(final String ip, final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		this.serverIp = ip;
		this.defaultTags = defaultTags;
		this.defaultRegistries = defaultRegistries;
	}

	// Client -> Proxy
	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(MODERN_PACKET_REGISTRY_KEY).set(ModernPacketRegistry.INSTANCE);

		channel.pipeline().addLast(new PacketSizer());
		channel.pipeline().addLast(ModernPacketDecoder.KEY, new ModernPacketDecoder());
		channel.pipeline().addLast(ModernPacketEncoder.KEY, new ModernPacketEncoder());

		final Connection connection = new Connection(this.serverIp);
		channel.pipeline().addLast(new PacketRewriterDecoder(this.serverIp, this.defaultTags, this.defaultRegistries));
		channel.pipeline().addLast(connection);
		channel.attr(CONNECTION_KEY).set(connection);
	}
}
