package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.networking.ProxyChannel;
import net.raphimc.netminecraft.netty.connection.NetServer;

import java.net.InetSocketAddress;

public final class Main {
	static void main(String[] args) {
		final String bindAddress = args[0];
		final int bindPort = Integer.parseInt(args[1]);
		final String serverAddress = args[2];
		IO.println(String.format("Listening on %s:%d -> %s", bindAddress, bindPort, serverAddress));
		new NetServer(new ProxyChannel(serverAddress))
				.bind(new InetSocketAddress(bindAddress, bindPort));
	}
}
