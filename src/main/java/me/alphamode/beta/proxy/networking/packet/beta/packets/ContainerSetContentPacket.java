package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

import java.util.List;

public class ContainerSetContentPacket implements Packet {
	public int containerId;
	public BetaItemStack[] items;

	public ContainerSetContentPacket() {
	}

	public ContainerSetContentPacket(final int containerId, final List<BetaItemStack> items) {
		this.containerId = containerId;
		this.items = new BetaItemStack[items.size()];
		for (int i = 0; i < this.items.length; i++) {
			this.items[i] = items.get(i);
		}
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readByte();
		final int size = buf.readShort();
		this.items = new BetaItemStack[size];
		for (int i = 0; i < size; i++) {
			this.items[i] = BetaItemStack.OPTIONAL_CODEC.decode(buf);
		}
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
		buf.writeShort(this.items.length);
		for (final BetaItemStack item : this.items) {
			BetaItemStack.OPTIONAL_CODEC.encode(buf, item);
		}
	}
}
