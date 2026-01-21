package me.alphamode.beta.proxy.networking;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

public final class C2PChannel extends SimpleChannelInboundHandler<RecordPacket<BetaPackets>> {
	private final String realServerIp;
	private NetClient realServer;

	public C2PChannel(final String ip) {
		this.realServerIp = ip;
	}

	@Override
	public void channelActive(final ChannelHandlerContext context) {
		if (this.realServer == null) {
			this.realServer = new NetClient(new P2SChannel(context.channel()));
			this.realServer.connect(MinecraftServerAddress.ofResolved(this.realServerIp)).syncUninterruptibly();
			this.realServer.getChannel().pipeline().addLast(new SimpleChannelInboundHandler<>() {
				@Override
				protected void channelRead0(final ChannelHandlerContext ctx, final Object msg) {
				}

				@Override
				public void channelInactive(final ChannelHandlerContext ctx) {
					context.close();
				}
			});
		}
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext context, final RecordPacket<BetaPackets> byteBuf) {
		this.realServer.getChannel().writeAndFlush(byteBuf);
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		this.realServer.getChannel().close().syncUninterruptibly();
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) throws Exception {
		super.exceptionCaught(context, cause);
	}
}
