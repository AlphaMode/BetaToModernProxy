package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class AnimatePacket implements Packet {
    public int id;
    public int action;

    public AnimatePacket() {
    }

    public AnimatePacket(int entityId, int action) {
        this.id = entityId;
        this.action = action;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.action = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeByte(this.action);
    }
}
