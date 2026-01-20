package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TeleportEntityPacket implements Packet {
    public int id;
    public int x;
    public int y;
    public int z;
    public byte yRot;
    public byte xRot;

    public TeleportEntityPacket() {
    }

//    public TeleportEntityPacket(Entity entity) {
//        this.id = entity.id;
//        this.x = Mth.floor(entity.x * 32.0);
//        this.y = Mth.floor(entity.y * 32.0);
//        this.z = Mth.floor(entity.z * 32.0);
//        this.yRot = (byte)(entity.yRot * 256.0F / 360.0F);
//        this.xRot = (byte)(entity.xRot * 256.0F / 360.0F);
//    }

    public TeleportEntityPacket(int id, int x, int y, int z, byte yRot, byte xRot) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yRot = yRot;
        this.xRot = xRot;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.yRot = data.readByte();
        this.xRot = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.yRot);
        data.writeByte(this.xRot);
    }
}
