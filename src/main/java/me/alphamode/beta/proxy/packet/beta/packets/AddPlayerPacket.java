package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class AddPlayerPacket implements Packet {
    public int id;
    public String name;
    public int x;
    public int y;
    public int z;
    public byte yRot;
    public byte xRot;
    public int carriedItem;

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.name = PacketUtils.readString(data, 16);
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.yRot = data.readByte();
        this.xRot = data.readByte();
        this.carriedItem = data.readShort();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        PacketUtils.writeString(this.name, data);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.yRot);
        data.writeByte(this.xRot);
        data.writeShort(this.carriedItem);
    }
}
