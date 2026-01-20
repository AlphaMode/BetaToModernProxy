package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TileEventPacket implements Packet {
    public int x;
    public int y;
    public int z;
    public int b0;
    public int b1;

    public TileEventPacket() {
    }

    public TileEventPacket(int x, int y, int z, int b0, int b1) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.b0 = b0;
        this.b1 = b1;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.y = data.readShort();
        this.z = data.readInt();
        this.b0 = data.readByte();
        this.b1 = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeShort(this.y);
        data.writeInt(this.z);
        data.writeByte(this.b0);
        data.writeByte(this.b1);
    }
}
