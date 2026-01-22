package me.alphamode.beta.proxy;

import me.alphamode.beta.proxy.networking.ProxyChannel;
import net.lenni0451.mcstructs.nbt.io.NbtIO;
import net.lenni0451.mcstructs.nbt.io.NbtReadTracker;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.raphimc.netminecraft.netty.connection.NetServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.zip.GZIPInputStream;

public final class Main {
	private static final CompoundTag DEFAULT_REGISTRIES;

	static {
		CompoundTag tag;
		try {
			tag = NbtIO.LATEST.readNamed(new DataInputStream(new GZIPInputStream(Objects.requireNonNull(Main.class.getResourceAsStream("/vanilla_registries.nbt")))), new NbtReadTracker()).getTag().asCompoundTag();
		} catch (final Exception exception) {
			tag = new CompoundTag();
		}

		DEFAULT_REGISTRIES = tag;
	}

	static void main(String[] args) throws IOException {
		final String bindAddress = args[0];
		final int bindPort = Integer.parseInt(args[1]);
		final String serverAddress = args[2];
		IO.println(String.format("Listening on %s:%d -> %s", bindAddress, bindPort, serverAddress));
		new NetServer(new ProxyChannel(serverAddress, DEFAULT_REGISTRIES))
				.bind(new InetSocketAddress(bindAddress, bindPort));
	}
}
