package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.alphamode.beta.proxy.networking.connection.ClientConnection;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

// ByteBuf -> Packet
public final class C2PChannel extends ChannelInboundHandlerAdapter {
	private final String realServerIp;
	private NetClient realServer;
	private ClientConnection connection;

	public C2PChannel(final String ip) {
		this.realServerIp = ip;
	}

	@Override
	public void channelActive(final ChannelHandlerContext context) {
		IO.println("Connection acquired!");
		if (this.realServer == null) {
			this.realServer = new NetClient(new P2SChannel(context.channel()));
			this.realServer.connect(MinecraftServerAddress.ofResolved(this.realServerIp)).addListener(future -> {
				if (!future.isSuccess()) {
					context.close();
				}
			});
			this.realServer.getChannel().pipeline().addLast(new ChannelInboundHandlerAdapter() {
				@Override
				public void channelRead(final ChannelHandlerContext ctx, final Object data) {
					final Channel clientChannel = context.channel();
					clientChannel.eventLoop().execute(() -> clientChannel.writeAndFlush(data));
				}

				@Override
				public void channelInactive(final ChannelHandlerContext ctx) {
					context.close();
				}
			});
		}

		this.connection = new ClientConnection(context.channel());
		context.channel().attr(ProxyChannel.CONNECTION_KEY).set(this.connection);
	}

	@Override
	public void channelRead(final ChannelHandlerContext ctx, final Object data) {
		if (this.realServer != null && this.realServer.getChannel().isActive()) {
			this.realServer.getChannel().writeAndFlush(data);
		}
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		IO.println("Connection lost!");
		if (this.realServer != null) {
			this.realServer.getChannel().close();
			this.realServer = null;
		}

		if (this.connection != null) {
			this.connection.disconnect();
			this.connection = null;
		}
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		cause.printStackTrace();
		context.close();
	}
}
