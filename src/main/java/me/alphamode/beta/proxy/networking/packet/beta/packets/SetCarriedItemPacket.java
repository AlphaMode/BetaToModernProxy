package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetCarriedItemPacket implements Packet {
	public int slot;

	public SetCarriedItemPacket() {
	}

	public SetCarriedItemPacket(final int slot) {
		this.slot = slot;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.slot = buf.readShort();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeShort(this.slot);
	}
}
