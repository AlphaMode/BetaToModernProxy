package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class AddPaintingPacket implements Packet {
    public int id;
    public int x;
    public int y;
    public int z;
    public int dir;
    public String motive;

    public AddPaintingPacket() {
    }

//    public AddPaintingPacket(Painting painting) {
//        this.id = painting.id;
//        this.x = painting.xTile;
//        this.y = painting.yTile;
//        this.z = painting.zTile;
//        this.dir = painting.direction;
//        this.motive = painting.motive.name;
//    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.motive = PacketUtils.readString(data, /*Painting.Motive.length*/13);
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.dir = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        PacketUtils.writeString(this.motive, data);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeInt(this.dir);
    }
}
