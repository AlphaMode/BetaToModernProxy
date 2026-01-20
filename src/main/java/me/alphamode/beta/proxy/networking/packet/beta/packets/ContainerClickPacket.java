package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerClickPacket implements Packet {
	public int containerId;
	public int slotNum;
	public int buttonNum;
	public short uid;
	public BetaItemStack item;
	public boolean quickMove;

	public ContainerClickPacket() {
	}

	public ContainerClickPacket(final int containerId, final int slotNum, final int buttonNum, final boolean quickMove, final BetaItemStack carriedItem, final short backup) {
		this.containerId = containerId;
		this.slotNum = slotNum;
		this.buttonNum = buttonNum;
		this.item = carriedItem;
		this.uid = backup;
		this.quickMove = quickMove;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.containerId = buf.readByte();
		this.slotNum = buf.readShort();
		this.buttonNum = buf.readByte();
		this.uid = buf.readShort();
		this.quickMove = buf.readBoolean();
		this.item = BetaItemStack.OPTIONAL_CODEC.decode(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeByte(this.containerId);
		buf.writeShort(this.slotNum);
		buf.writeByte(this.buttonNum);
		buf.writeShort(this.uid);
		buf.writeBoolean(this.quickMove);
		BetaItemStack.OPTIONAL_CODEC.encode(buf, this.item);
	}
}
