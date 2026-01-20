package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class GameEventPacket implements Packet {
	public int event;

	public GameEventPacket() {
	}

	public GameEventPacket(final int event) {
		this.event = event;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.event = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.event);
	}
}
