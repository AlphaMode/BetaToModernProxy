package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketReader;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketWriter;
import net.raphimc.netminecraft.netty.codec.PacketSizer;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

public final class Client2ProxyChannel extends ChannelInitializer<Channel> {
	private final MinecraftServerAddress address;

	public Client2ProxyChannel(final MinecraftServerAddress address) {
		this.address = address;
	}

	@Override
	protected void initChannel(final Channel channel) {
		final Connection connection = new Connection(this.address);

		final ChannelPipeline pipeline = channel.pipeline();

		// Reads Prefixed Length & Splits Packets
		pipeline.addLast(new PacketSizer());

		// ByteBuf -> ModernPacket
		pipeline.addLast(ModernPacketReader.KEY, new ModernPacketReader(connection));

		pipeline.addLast(connection);

		pipeline.addLast(ModernPacketWriter.KEY, new ModernPacketWriter());

		pipeline.addLast(new PacketSizer());

		// _
		pipeline.addLast(new SimpleChannelInboundHandler<ByteBuf>() {
			@Override
			protected void channelRead0(final ChannelHandlerContext context, final ByteBuf buf) {
				context.channel().writeAndFlush(buf);
			}
		});
	}
}
