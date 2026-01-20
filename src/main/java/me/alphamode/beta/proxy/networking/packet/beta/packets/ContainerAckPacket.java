package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerAckPacket implements Packet {
	public int containerId;
	public short uid;
	public boolean accepted;

	public ContainerAckPacket() {
	}

	public ContainerAckPacket(final int containerId, final short uid, final boolean accepted) {
		this.containerId = containerId;
		this.uid = uid;
		this.accepted = accepted;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readUnsignedByte();
		this.uid = buf.readShort();
		this.accepted = buf.readBoolean();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
		buf.writeShort(this.uid);
		buf.writeBoolean(this.accepted);
	}
}
