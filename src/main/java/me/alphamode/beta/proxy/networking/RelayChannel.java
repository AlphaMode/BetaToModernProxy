package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketRegistry;

// Packet -> ByteBuf
public final class RelayChannel extends ChannelInitializer<Channel> {
	private final Channel otherChannel;

	public RelayChannel(final Channel channel) {
		this.otherChannel = channel;
		if (channel == null) {
			throw new RuntimeException("Cannot create P2S connection! Channel is null!");
		}
	}

	// Server -> Proxy
	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(ProxyChannel.BETA_PACKET_REGISTRY_KEY).set(BetaPacketRegistry.INSTANCE);
		channel.pipeline().addLast(BetaPacketDecoder.KEY, new BetaPacketDecoder());
		channel.pipeline().addLast(new SimpleChannelInboundHandler<RecordPacket<?>>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final RecordPacket<?> packet) {
				BrodernProxy.LOGGER.info("meow");
				otherChannel.writeAndFlush(packet);
			}
		});
	}
}
