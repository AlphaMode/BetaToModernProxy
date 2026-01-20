package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.packet.Packet;

public final class P2SChannel extends ChannelInitializer<Channel> {
	private final Channel otherChannel;

	public P2SChannel(final Channel channel) {
		this.otherChannel = channel;
		if (channel == null) {
			throw new RuntimeException("Cannot create P2S connection! Channel is null!");
		}
	}

	@Override
	protected void initChannel(Channel channel) {
		channel.attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).set(BetaPacketRegistry.INSTANCE);
		channel.pipeline().addLast(BetaPacketEncoder.KEY, new BetaPacketEncoder());
		channel.pipeline().addLast(BetaPacketDecoder.KEY, new BetaPacketDecoder());
		channel.pipeline().addLast(new SimpleChannelInboundHandler<Packet>() {
			@Override
			protected void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet) {
				otherChannel.writeAndFlush(packet);
			}
		});
	}
}
