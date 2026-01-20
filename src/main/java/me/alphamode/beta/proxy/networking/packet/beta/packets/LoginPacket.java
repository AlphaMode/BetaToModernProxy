package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import net.raphimc.netminecraft.packet.Packet;

public class LoginPacket implements Packet {
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> USERNAME_CODEC = ByteBufCodecs.stringUtf8(MAX_USERNAME_LENGTH);

	public int clientVersion;
	public String username;
	public long seed;
	public byte dimension;

	public LoginPacket() {
	}

	public LoginPacket(String username, int protocol) {
		this.username = username;
		this.clientVersion = protocol;
	}

	public LoginPacket(final String username, final int protocol, final long seed, final byte dimension) {
		this.username = username;
		this.clientVersion = protocol;
		this.seed = seed;
		this.dimension = dimension;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.clientVersion = buf.readInt();
		this.username = USERNAME_CODEC.decode(buf);
		this.seed = buf.readLong();
		this.dimension = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.clientVersion);
		USERNAME_CODEC.encode(buf, this.username);
		buf.writeLong(this.seed);
		buf.writeByte(this.dimension);
	}
}
