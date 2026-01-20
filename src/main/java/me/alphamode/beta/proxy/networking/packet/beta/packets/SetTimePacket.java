package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetTimePacket implements Packet {
	public long time;

	public SetTimePacket() {
	}

	public SetTimePacket(final long time) {
		this.time = time;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.time = buf.readLong();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeLong(this.time);
	}
}
