package me.alphamode.beta.proxy.networking;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketReader;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketWriter;
import net.raphimc.netminecraft.netty.codec.PacketSizer;
import net.raphimc.netminecraft.util.MinecraftServerAddress;

public final class ProxyChannelInitializer extends ChannelInitializer<Channel> {
	private final MinecraftServerAddress address;

	public ProxyChannelInitializer(final MinecraftServerAddress address) {
		this.address = address;
	}

	public MinecraftServerAddress getAddress() {
		return this.address;
	}

	@Override
	protected void initChannel(final Channel channel) {
		final ClientConnection connection = new ClientConnection(this.address);
		final ChannelPipeline pipeline = channel.pipeline();

		// Reads Prefixed Length & Splits Packets
		pipeline.addLast(new PacketSizer());

		// ByteBuf -> ModernPacket
		pipeline.addLast(ModernPacketReader.KEY, new ModernPacketReader(connection));

		pipeline.addLast(connection);

		pipeline.addLast(ModernPacketWriter.KEY, new ModernPacketWriter());

		pipeline.addLast(new PacketSizer());
	}
}
