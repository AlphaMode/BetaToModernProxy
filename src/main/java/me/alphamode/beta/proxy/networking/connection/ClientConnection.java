package me.alphamode.beta.proxy.networking.connection;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

public final class ClientConnection {
	private final Channel channel;

	public ClientConnection(final Channel channel) {
		this.channel = channel;
	}

	public void write(final ByteBuf buf) {
		if (this.isConnected()) {
			this.channel.writeAndFlush(buf);
		} else {
			throw new RuntimeException("Cannot write to dead connection!");
		}
	}

	public void disconnect() {
		if (this.isConnected()) {
			this.channel.close();
		}
	}

	public boolean isConnected() {
		return this.channel.isActive();
	}
}
