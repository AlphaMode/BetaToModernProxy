package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;

// Packet -> ByteBuf
public final class RelayChannel extends ChannelInitializer<Channel> {
	private final Channel otherChannel;

	public RelayChannel(final Channel channel) {
		this.otherChannel = channel;
		if (channel == null) {
			throw new RuntimeException("Cannot create P2S connection! Channel is null!");
		}
	}

	// Proxy -> Server
	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(ProxyChannel.BETA_PACKET_REGISTRY_KEY).set(BetaPacketRegistry.INSTANCE);
		channel.pipeline().addLast(BetaPacketEncoder.KEY, new BetaPacketEncoder());
		channel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
			@Override
			public void channelRead(final ChannelHandlerContext ctx, final Object message) {
				otherChannel.writeAndFlush(message);
			}
		});
	}
}
