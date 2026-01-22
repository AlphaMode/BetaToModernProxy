package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import net.raphimc.netminecraft.netty.connection.NetClient;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

public final class Connection extends SimpleChannelInboundHandler<RecordPacket<?>> {
	private final String realServerIp;
	private NetClient realServer;
	private Channel channel;

	public Connection(final String ip) {
		this.realServerIp = ip;
	}

	public void write(final ByteBuf buf) {
		this.channel.write(buf);
	}

	public void disconnect() {
		if (this.isConnected()) {
			this.channel.close();
			this.channel = null;
		}
	}

	public boolean isConnected() {
		return this.channel != null && this.channel.isActive();
	}

	@Override
	protected void channelRead0(final ChannelHandlerContext ctx, final RecordPacket<?> packet) throws Exception {
		if (this.realServer != null && this.realServer.getChannel().isActive()) {
			this.realServer.getChannel().writeAndFlush(packet);
		}
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

		this.channel = context.channel();
	}

	@Override
	public void channelInactive(final ChannelHandlerContext context) {
		IO.println("Connection lost!");
		if (this.realServer != null) {
			this.realServer.getChannel().close();
			this.realServer = null;
		}
	}

	@Override
	public void exceptionCaught(final ChannelHandlerContext context, final Throwable cause) {
		cause.printStackTrace();
		context.close();
	}
}
