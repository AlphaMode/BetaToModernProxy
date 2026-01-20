package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TileUpdatePacket implements Packet {
	public int x;
	public int y;
	public int z;
	public int block;
	public int data;

	public TileUpdatePacket() {
//        this.shouldDelay = true;
	}

	public TileUpdatePacket(final int x, final int y, final int z, final int block, final int data) {
//        this.shouldDelay = true;
		this.x = x;
		this.y = y;
		this.z = z;
		this.block = block;
		this.data = data;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.x = buf.readInt();
		this.y = buf.readByte();
		this.z = buf.readInt();
		this.block = buf.readByte();
		this.data = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.x);
		buf.writeByte(this.y);
		buf.writeInt(this.z);
		buf.writeByte(this.block);
		buf.writeByte(this.data);
	}
}
