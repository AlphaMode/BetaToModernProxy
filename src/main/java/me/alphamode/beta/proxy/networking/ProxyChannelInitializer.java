package me.alphamode.beta.proxy.networking;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacketCodec;
import net.raphimc.netminecraft.netty.codec.PacketCryptor;
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
		pipeline.addLast(new PacketCryptor());
		pipeline.addLast(new PacketSizer());
		pipeline.addLast(ModernPacketCodec.KEY, new ModernPacketCodec(connection));
		pipeline.addLast(connection);
	}
}
