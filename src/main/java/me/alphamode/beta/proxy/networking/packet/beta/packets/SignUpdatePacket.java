package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import me.alphamode.beta.proxy.util.StreamCodec;
import net.raphimc.netminecraft.packet.Packet;

public class SignUpdatePacket implements Packet {
	public static final int MAX_SIGN_STRING_LENGTH = 15;
	public static final StreamCodec<ByteBuf, String> SIGN_STRING_CODEC = ByteBufCodecs.stringUtf8(MAX_SIGN_STRING_LENGTH);

	public int x;
	public int y;
	public int z;
	public String[] lines;

	public SignUpdatePacket() {
//        this.shouldDelay = true;
	}

	public SignUpdatePacket(int x, int y, int z, String[] lines) {
//        this.shouldDelay = true;
		this.x = x;
		this.y = y;
		this.z = z;
		this.lines = lines;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.x = buf.readInt();
		this.y = buf.readShort();
		this.z = buf.readInt();
		this.lines = new String[4];
		for (int i = 0; i < 4; i++) {
			this.lines[i] = SIGN_STRING_CODEC.decode(buf);
		}
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.x);
		buf.writeShort(this.y);
		buf.writeInt(this.z);
		for (int i = 0; i < 4; i++) {
			SIGN_STRING_CODEC.encode(buf, this.lines[i]);
		}
	}
}
