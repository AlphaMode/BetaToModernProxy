package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetHealthPacket implements Packet {
	public int health;

	public SetHealthPacket() {
	}

	public SetHealthPacket(final int health) {
		this.health = health;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.health = buf.readShort();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeShort(this.health);
	}
}
