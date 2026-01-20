package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class LevelEventPacket implements Packet {
    public int type;
    public int data;
    public int x;
    public int y;
    public int z;

    public LevelEventPacket() {
    }

    public LevelEventPacket(int type, int x, int y, int z, int data) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.data = data;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.type = data.readInt();
        this.x = data.readInt();
        this.y = data.readByte();
        this.z = data.readInt();
        this.data = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.type);
        data.writeInt(this.x);
        data.writeByte(this.y);
        data.writeInt(this.z);
        data.writeInt(this.data);
    }
}
