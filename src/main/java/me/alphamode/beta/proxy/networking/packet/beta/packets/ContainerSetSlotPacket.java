package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerSetSlotPacket implements Packet {
	public int containerId;
	public int slot;
	public BetaItemStack item;

	public ContainerSetSlotPacket() {
	}

	public ContainerSetSlotPacket(int containerId, int slot, BetaItemStack item) {
		this.containerId = containerId;
		this.slot = slot;
		this.item = item;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readByte();
		this.slot = buf.readShort();
		this.item = BetaItemStack.OPTIONAL_CODEC.decode(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
		buf.writeShort(this.slot);
		BetaItemStack.OPTIONAL_CODEC.encode(buf, this.item);
	}
}
