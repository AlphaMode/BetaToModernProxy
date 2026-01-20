package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TileEventPacket implements Packet {
	public int x;
	public int y;
	public int z;
	public int b0;
	public int b1;

	public TileEventPacket() {
	}

	public TileEventPacket(final int x, final int y, final int z, final int b0, final int b1) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.b0 = b0;
		this.b1 = b1;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.x = buf.readInt();
		this.y = buf.readShort();
		this.z = buf.readInt();
		this.b0 = buf.readByte();
		this.b1 = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.x);
		buf.writeShort(this.y);
		buf.writeInt(this.z);
		buf.writeByte(this.b0);
		buf.writeByte(this.b1);
	}
}
