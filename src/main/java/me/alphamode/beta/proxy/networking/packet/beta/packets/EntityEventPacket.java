package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class EntityEventPacket implements Packet {
	public int entityId;
	public byte eventId;

	public EntityEventPacket() {
	}

	public EntityEventPacket(final int entityId, final byte eventId) {
		this.entityId = entityId;
		this.eventId = eventId;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.entityId = buf.readInt();
		this.eventId = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.entityId);
		buf.writeByte(this.eventId);
	}
}
