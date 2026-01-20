package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerSetDataPacket implements Packet {
	public int containerId;
	public int id;
	public int value;

	public ContainerSetDataPacket() {
	}

	public ContainerSetDataPacket(final int containerId, final int id, final int value) {
		this.containerId = containerId;
		this.id = id;
		this.value = value;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readUnsignedByte();
		this.id = buf.readShort();
		this.value = buf.readShort();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
		buf.writeShort(this.id);
		buf.writeShort(this.value);
	}
}
