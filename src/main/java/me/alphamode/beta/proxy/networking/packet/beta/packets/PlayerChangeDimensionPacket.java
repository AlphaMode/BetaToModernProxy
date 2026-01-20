package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class PlayerChangeDimensionPacket implements Packet {
	public byte dimension;

	public PlayerChangeDimensionPacket() {
	}

	public PlayerChangeDimensionPacket(final byte dimension) {
		this.dimension = dimension;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.dimension = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.dimension);
	}
}
