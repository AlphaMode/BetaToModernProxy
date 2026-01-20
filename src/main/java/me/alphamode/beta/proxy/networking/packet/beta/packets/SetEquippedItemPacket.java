package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

public class SetEquippedItemPacket implements Packet {
	public int entityId;
	public int slot;
	public int item;
	public int auxValue;

	public SetEquippedItemPacket() {
	}

	public SetEquippedItemPacket(final int entityId, final int slot, final BetaItemStack item) {
		this.entityId = entityId;
		this.slot = slot;
		if (item == null) {
			this.item = -1;
			this.auxValue = 0;
		} else {
			this.item = item.id();
			this.auxValue = item.aux();
		}
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.entityId = buf.readInt();
		this.slot = buf.readShort();
		this.item = buf.readShort();
		this.auxValue = buf.readShort();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.entityId);
		buf.writeShort(this.slot);
		buf.writeShort(this.item);
		buf.writeShort(this.auxValue);
	}
}
