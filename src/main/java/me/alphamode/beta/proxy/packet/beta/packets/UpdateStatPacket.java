package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class UpdateStatPacket implements Packet {
    public int id;
    public int amount;

    public UpdateStatPacket() {
    }

    public UpdateStatPacket(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.amount = data.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeByte(this.amount);
    }
}
