package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.data.BetaItemStack;
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
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readByte();
        this.slot = data.readShort();
        this.item = BetaItemStack.OPTIONAL_CODEC.decode(data);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
        data.writeShort(this.slot);
        BetaItemStack.OPTIONAL_CODEC.encode(data, this.item);
    }
}
