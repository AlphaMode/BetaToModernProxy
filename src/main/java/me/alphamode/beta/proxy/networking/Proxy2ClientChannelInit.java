package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketReader;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacketWriter;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Proxy2ClientChannelInit extends ChannelInitializer<Channel> {
	private static final Logger LOGGER = LogManager.getLogger(Proxy2ClientChannelInit.class);
	private final Connection connection;

	public Proxy2ClientChannelInit(final Connection connection) {
		this.connection = connection;
	}

	// Proxy -> Client
	@Override
	protected void initChannel(final Channel channel) {
		final ChannelPipeline pipeline = channel.pipeline();

		// ByteBuf -> BetaPacket
		pipeline.addLast(BetaPacketReader.KEY, new BetaPacketReader());

        // Connection consumes BetaPacket and rewrites to a modern packet and sends it to the client
        pipeline.addLast("rewriter", new SimpleChannelInboundHandler<BetaRecordPacket>() {
            @Override
            protected void channelRead0(ChannelHandlerContext ctx, BetaRecordPacket msg) throws Exception {
                connection.rewritePacketB2C(msg);
            }
        });

        // BetaPacket -> ByteBuf
        pipeline.addLast(BetaPacketWriter.KEY, new BetaPacketWriter());

		pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final ByteBuf buf) {
				context.channel().writeAndFlush(buf);
			}
		});
	}

	@Override
	public void channelInactive(final ChannelHandlerContext ctx) {
		this.connection.disconnect();
	}
}
