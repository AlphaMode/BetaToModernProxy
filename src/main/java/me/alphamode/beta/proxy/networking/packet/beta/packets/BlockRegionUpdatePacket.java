package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class BlockRegionUpdatePacket implements Packet {
	public int x;
	public int y;
	public int z;
	public int xs;
	public int ys;
	public int zs;
	public byte[] buffer;
	private int size;

	public BlockRegionUpdatePacket() {
//        this.shouldDelay = true;
	}

	public BlockRegionUpdatePacket(int x, int y, int z, int xs, int ys, int zs, byte[] data) {
//        this.shouldDelay = true;
		this.x = x;
		this.y = y;
		this.z = z;
		this.xs = xs;
		this.ys = ys;
		this.zs = zs;
		this.buffer = data;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.x = buf.readInt();
		this.y = buf.readShort();
		this.z = buf.readInt();
		this.xs = buf.readByte() + 1;
		this.ys = buf.readByte() + 1;
		this.zs = buf.readByte() + 1;
		this.size = buf.readInt();
		final byte[] data = new byte[this.size];
		buf.readBytes(data);
		this.buffer = data;
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.x);
		buf.writeShort(this.y);
		buf.writeInt(this.z);
		buf.writeByte(this.xs - 1);
		buf.writeByte(this.ys - 1);
		buf.writeByte(this.zs - 1);
		buf.writeInt(this.size);
		buf.writeBytes(this.buffer);
	}
}
