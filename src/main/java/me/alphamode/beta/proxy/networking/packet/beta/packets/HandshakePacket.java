package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import net.raphimc.netminecraft.packet.Packet;

public class HandshakePacket implements Packet {
	public static final int MAX_USERNAME_LENGTH = 32;
	public static final StreamCodec<ByteBuf, String> USERNAME_CODEC = ByteBufCodecs.stringUtf8(MAX_USERNAME_LENGTH);

	public String username;

	public HandshakePacket() {
	}

	public HandshakePacket(final String username) {
		this.username = username;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.username = USERNAME_CODEC.decode(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		USERNAME_CODEC.encode(buf, this.username);
	}
}
