package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class LevelEventPacket implements Packet {
	public int type;
	public int data;
	public int x;
	public int y;
	public int z;

	public LevelEventPacket() {
	}

	public LevelEventPacket(final int type, final int x, final int y, final int z, final int data) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.data = data;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.type = buf.readInt();
		this.x = buf.readInt();
		this.y = buf.readByte();
		this.z = buf.readInt();
		this.data = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.type);
		buf.writeInt(this.x);
		buf.writeByte(this.y);
		buf.writeInt(this.z);
		buf.writeInt(this.data);
	}
}
