package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class InteractionPacket implements Packet {
	public int id;
	public int x;
	public int y;
	public int z;
	public int type;

	public InteractionPacket() {
	}

	public InteractionPacket(final int entityId, final int type, final int x, final int y, final int z) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = entityId;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.type = buf.readByte();
		this.x = buf.readInt();
		this.y = buf.readByte();
		this.z = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.type);
		buf.writeInt(this.x);
		buf.writeByte(this.y);
		buf.writeInt(this.z);
	}
}
