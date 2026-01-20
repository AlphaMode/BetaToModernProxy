package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.data.BetaItemStack;
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

    public ContainerClickPacket(int containerId, int slotNum, int buttonNum, boolean quickMove, BetaItemStack carriedItem, short backup) {
        this.containerId = containerId;
        this.slotNum = slotNum;
        this.buttonNum = buttonNum;
        this.item = carriedItem;
        this.uid = backup;
        this.quickMove = quickMove;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readByte();
        this.slotNum = data.readShort();
        this.buttonNum = data.readByte();
        this.uid = data.readShort();
        this.quickMove = data.readBoolean();
        this.item = BetaItemStack.OPTIONAL_CODEC.decode(data);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
        data.writeShort(this.slotNum);
        data.writeByte(this.buttonNum);
        data.writeShort(this.uid);
        data.writeBoolean(this.quickMove);
        BetaItemStack.OPTIONAL_CODEC.encode(data, this.item);
    }
}
