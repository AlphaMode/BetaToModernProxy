package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class RemoveEntityPacket implements Packet {
	public int id;

	public RemoveEntityPacket() {
	}

	public RemoveEntityPacket(int entityId) {
		this.id = entityId;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
	}
}
