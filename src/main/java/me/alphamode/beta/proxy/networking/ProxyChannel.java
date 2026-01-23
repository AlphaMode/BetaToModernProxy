package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketReader;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketWriter;
import me.alphamode.beta.proxy.rewriter.PacketRewriterDecoder;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.raphimc.netminecraft.netty.codec.PacketSizer;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

// Packets
public final class ProxyChannel extends ChannelInitializer<Channel> {
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
		// Reads Prefixed Length & Splits Packets
		channel.pipeline().addLast(new PacketSizer());

		// ByteBuf -> ModernPacket
		channel.pipeline().addLast(ModernPacketReader.KEY, new ModernPacketReader());

		// ModernPacket -> BetaPacket (Rewriting)
		channel.pipeline().addLast(new PacketRewriterDecoder(this.defaultTags, this.defaultRegistries));

		// BetaPacket -> ByteBuf
		channel.pipeline().addLast(ModernPacketWriter.KEY, new BetaPacketWriter());

		final Connection connection = new Connection(MinecraftServerAddress.ofResolved(this.serverIp));
		channel.pipeline().addLast(connection);
		channel.attr(CONNECTION_KEY).set(connection);
	}
}
