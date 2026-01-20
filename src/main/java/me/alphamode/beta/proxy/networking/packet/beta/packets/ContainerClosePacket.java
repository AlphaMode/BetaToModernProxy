package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerClosePacket implements Packet {
	public int containerId;

	public ContainerClosePacket() {
	}

	public ContainerClosePacket(final int containerId) {
		this.containerId = containerId;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
	}
}
