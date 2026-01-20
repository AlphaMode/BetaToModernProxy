package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetCarriedItemPacket implements Packet {
    public int slot;

    public SetCarriedItemPacket() {
    }

    public SetCarriedItemPacket(int slot) {
        this.slot = slot;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.slot = data.readShort();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeShort(this.slot);
    }
}
