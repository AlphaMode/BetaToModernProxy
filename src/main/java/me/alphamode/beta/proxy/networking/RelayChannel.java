package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketReader;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketWriter;
import me.alphamode.beta.proxy.rewriter.PacketRewriterEncoder;
import net.raphimc.netminecraft.netty.codec.PacketSizer;

public final class RelayChannel extends ChannelInitializer<Channel> {
	private final Channel otherChannel;

	public RelayChannel(final Channel channel) {
		this.otherChannel = channel;
		if (channel == null) {
			throw new RuntimeException("Cannot create P2S connection! Channel is null!");
		}
	}

	// Server -> Proxy -> Client
	@Override
	protected void initChannel(final Channel channel) {
		// ByteBuf -> BetaPacket
		channel.pipeline().addLast(BetaPacketReader.KEY, new BetaPacketReader());

		// BetaPacket -> ModernPacket (Rewriting)
		channel.pipeline().addLast(PacketRewriterEncoder.KEY, new PacketRewriterEncoder());

		// ModernPacket -> ByteBuf
		channel.pipeline().addLast(ModernPacketWriter.KEY, new ModernPacketWriter());

		// Writes pre-fixed length
		channel.pipeline().addLast(new PacketSizer());

		// ByteBuf -> Client
		channel.pipeline().addLast(new SimpleChannelInboundHandler<>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final Object buf) {
				BrodernProxy.LOGGER.info("Sending Packet to Client: {}", buf);
				otherChannel.writeAndFlush(buf);
			}
		});
	}
}
