package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class PlayerCommandPacket implements Packet {
	public int id;
	public int action;

	public PlayerCommandPacket() {
	}

	public PlayerCommandPacket(final int entityId, final int action) {
		this.id = entityId;
		this.action = action;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.action = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.action);
	}
}
