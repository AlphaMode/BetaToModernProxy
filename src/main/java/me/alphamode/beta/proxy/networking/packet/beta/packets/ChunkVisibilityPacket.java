package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ChunkVisibilityPacket implements Packet {
	public int x;
	public int z;
	public boolean visible;

	public ChunkVisibilityPacket() {
//        this.shouldDelay = false;
	}

	public ChunkVisibilityPacket(final int x, final int z, final boolean visible) {
//        this.shouldDelay = false;
		this.x = x;
		this.z = z;
		this.visible = visible;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.x = buf.readInt();
		this.z = buf.readInt();
		this.visible = buf.readByte() != 0;
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.x);
		buf.writeInt(this.z);
		buf.writeByte(this.visible ? 1 : 0);
	}
}
