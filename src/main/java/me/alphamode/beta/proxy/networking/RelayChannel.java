package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketReader;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketWriter;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;

public final class RelayChannel extends ChannelInitializer<Channel> {
	private final Connection connection;

	public RelayChannel(final Connection connection) {
		this.connection = connection;
	}

	// Server -> Proxy -> Client
	@Override
	protected void initChannel(final Channel channel) {
		channel.pipeline().addLast(ModernPacketReader.KEY, new ModernPacketReader());
		channel.pipeline().addLast(new SimpleChannelInboundHandler<ModernRecordPacket<?>>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext ctx, final ModernRecordPacket<?> buf) {
				BrodernProxy.LOGGER.info("Sending Packet to Client: {}", buf);
			}
		});
		channel.pipeline().addLast(ModernPacketWriter.KEY, new ModernPacketWriter());
		channel.pipeline().addLast(new SimpleChannelInboundHandler<ByteBuf>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext ctx, final ByteBuf buf) {
				connection.write(buf);
			}
		});
	}
}
