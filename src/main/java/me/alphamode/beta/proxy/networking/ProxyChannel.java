package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketReader;
import me.alphamode.beta.proxy.rewriter.PacketRewriterDecoder;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.raphimc.netminecraft.netty.codec.PacketSizer;
import net.raphimc.netminecraft.util.MinecraftServerAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Packets
public final class ProxyChannel extends ChannelInitializer<Channel> {
	private static final Logger LOGGER = LogManager.getLogger(ProxyChannel.class);
	public static final AttributeKey<Connection> CONNECTION_KEY = AttributeKey.newInstance("connection");
	private final String serverIp;
	private final CompoundTag defaultTags;
	private final CompoundTag defaultRegistries;

	public ProxyChannel(final String ip, final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		this.serverIp = ip;
		this.defaultTags = defaultTags;
		this.defaultRegistries = defaultRegistries;
	}

	// Client -> Proxy -> Server
	@Override
	protected void initChannel(final Channel channel) {
		// Reads Prefixed Length & Splits Packets
		channel.pipeline().addLast(new PacketSizer());

		// ByteBuf -> ModernPacket
		channel.pipeline().addLast(ModernPacketReader.KEY, new ModernPacketReader());

		// ModernPacket -> BetaPacket (Rewriting)
		channel.pipeline().addLast(PacketRewriterDecoder.KEY, new PacketRewriterDecoder(this.defaultTags, this.defaultRegistries));

		// BetaPacket -> ByteBuf
		channel.pipeline().addLast(BetaPacketWriter.KEY, new BetaPacketWriter());

		final Connection connection = new Connection(MinecraftServerAddress.ofResolved(this.serverIp));
		channel.pipeline().addLast(connection);
		channel.attr(CONNECTION_KEY).set(connection);
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
		super.channelRead(ctx, msg);
	}

	@Override
	public void channelActive(final ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
	}
}
