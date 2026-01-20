package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class UpdateStatPacket implements Packet {
	public int id;
	public int amount;

	public UpdateStatPacket() {
	}

	public UpdateStatPacket(final int id, final int amount) {
		this.id = id;
		this.amount = amount;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.amount = buf.readUnsignedByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.amount);
	}
}
