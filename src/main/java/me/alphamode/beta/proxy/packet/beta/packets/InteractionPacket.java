package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class InteractionPacket implements Packet {
    public int id;
    public int x;
    public int y;
    public int z;
    public int type;

    public InteractionPacket() {
    }

    public InteractionPacket(int entityId, int type, int x, int y, int z) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.id = entityId;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.type = data.readByte();
        this.x = data.readInt();
        this.y = data.readByte();
        this.z = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeByte(this.type);
        data.writeInt(this.x);
        data.writeByte(this.y);
        data.writeInt(this.z);
    }
}
