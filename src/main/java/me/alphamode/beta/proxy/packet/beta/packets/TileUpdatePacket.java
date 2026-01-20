package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TileUpdatePacket implements Packet {
    public int x;
    public int y;
    public int z;
    public int block;
    public int data;

    public TileUpdatePacket() {
//        this.shouldDelay = true;
    }

    public TileUpdatePacket(int x, int y, int z, int block, int data) {
//        this.shouldDelay = true;
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
        this.data = data;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.y = data.readByte();
        this.z = data.readInt();
        this.block = data.readByte();
        this.data = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeByte(this.y);
        data.writeInt(this.z);
        data.writeByte(this.block);
        data.writeByte(this.data);
    }
}
