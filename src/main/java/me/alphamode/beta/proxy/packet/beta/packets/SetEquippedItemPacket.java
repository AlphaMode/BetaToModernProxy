package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.data.BetaItemStack;
import net.raphimc.netminecraft.packet.Packet;

public class SetEquippedItemPacket implements Packet {
    public int entity;
    public int slot;
    public int item;
    public int auxValue;

    public SetEquippedItemPacket() {
    }

    public SetEquippedItemPacket(int entity, int slot, BetaItemStack item) {
        this.entity = entity;
        this.slot = slot;
        if (item == null) {
            this.item = -1;
            this.auxValue = 0;
        } else {
            this.item = item.id();
            this.auxValue = item.auxValue();
        }
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.entity = data.readInt();
        this.slot = data.readShort();
        this.item = data.readShort();
        this.auxValue = data.readShort();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.entity);
        data.writeShort(this.slot);
        data.writeShort(this.item);
        data.writeShort(this.auxValue);
    }
}
