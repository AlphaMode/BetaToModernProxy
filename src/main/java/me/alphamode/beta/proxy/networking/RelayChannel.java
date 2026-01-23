package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketRegistry;

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
		// ByteBuf -> BetaPacket
		channel.pipeline().addLast(BetaPacketEncoder.KEY, new BetaPacketEncoder());
		// BetaPacket -> ModernPacket
//		channel.pipeline().addLast(new PacketRewriterEncoder());
		// ModernPacket -> ByteBuf?
		channel.pipeline().addLast(new SimpleChannelInboundHandler<>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final Object buf) {
				BrodernProxy.LOGGER.info("Sending Packet to Client: {}", buf);
				otherChannel.writeAndFlush(buf);
			}
		});
	}
}
