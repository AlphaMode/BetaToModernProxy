package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class AddItemEntityPacket implements Packet {
    public int entity;
    public int x;
    public int y;
    public int z;
    public byte xa;
    public byte ya;
    public byte za;
    public int item;
    public int count;
    public int auxValue;

    public AddItemEntityPacket() {
    }

//    public AddItemEntityPacket(ItemEntity item) {
//        this.entity = item.id;
//        this.item = item.item.id;
//        this.count = item.item.count;
//        this.auxValue = item.item.getAuxValue();
//        this.x = Mth.floor(item.x * 32.0);
//        this.y = Mth.floor(item.y * 32.0);
//        this.z = Mth.floor(item.z * 32.0);
//        this.xa = (byte)(item.xd * 128.0);
//        this.ya = (byte)(item.yd * 128.0);
//        this.za = (byte)(item.zd * 128.0);
//    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.entity = data.readInt();
        this.item = data.readShort();
        this.count = data.readByte();
        this.auxValue = data.readShort();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.xa = data.readByte();
        this.ya = data.readByte();
        this.za = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.entity);
        data.writeShort(this.item);
        data.writeByte(this.count);
        data.writeShort(this.auxValue);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.xa);
        data.writeByte(this.ya);
        data.writeByte(this.za);
    }
}
