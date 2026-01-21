package me.alphamode.beta.proxy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.AttributeKey;
import me.alphamode.beta.proxy.networking.C2PChannel;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketDecoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketEncoder;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPacketRegistry;
import net.raphimc.netminecraft.netty.connection.NetServer;

import java.net.InetSocketAddress;

public final class Proxy extends ChannelInitializer<Channel> {
	public static final AttributeKey<BetaPacketRegistry> PACKET_REGISTRY_ATTRIBUTE_KEY = AttributeKey.valueOf("beta_packet_registry");
	private final String serverIp;

	public Proxy(final String ip) {
		this.serverIp = ip;
	}

	static void main(String[] args) {
		final String bindAddress = args[0];
		final int bindPort = Integer.parseInt(args[1]);
		final String serverAddress = args[2];
		IO.println(String.format("Listening on %s:%d -> %s", bindAddress, bindPort, serverAddress));
		new NetServer(new Proxy(serverAddress))
				.bind(new InetSocketAddress(bindAddress, bindPort));
	}

	@Override
	protected void initChannel(final Channel channel) {
		channel.attr(PACKET_REGISTRY_ATTRIBUTE_KEY).set(BetaPacketRegistry.INSTANCE);
		channel.pipeline().addLast(BetaPacketEncoder.KEY, new BetaPacketEncoder());
		channel.pipeline().addLast(BetaPacketDecoder.KEY, new BetaPacketDecoder());
		channel.pipeline().addLast(new C2PChannel(this.serverIp));
	}
}
