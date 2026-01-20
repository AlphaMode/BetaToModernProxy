package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class AddEntityPacket implements Packet {
    public int id;
    public int x;
    public int y;
    public int z;
    public int xd;
    public int yd;
    public int zd;
    public int entityId;
    public int data;

    public AddEntityPacket() {
    }

//    @Environment(EnvType.SERVER)
//    public AddEntityPacket(Entity entity, int entityId) {
//        this(entity, entityId, 0);
//    }
//
//    @Environment(EnvType.SERVER)
//    public AddEntityPacket(Entity entity, int entityId, int data) {
//        this.id = entity.id;
//        this.x = Mth.floor(entity.x * 32.0);
//        this.y = Mth.floor(entity.y * 32.0);
//        this.z = Mth.floor(entity.z * 32.0);
//        this.entityId = entityId;
//        this.data = data;
//        if (data > 0) {
//            double d = entity.xd;
//            double e = entity.yd;
//            double f = entity.zd;
//            double g = 3.9;
//            if (d < -g) {
//                d = -g;
//            }
//
//            if (e < -g) {
//                e = -g;
//            }
//
//            if (f < -g) {
//                f = -g;
//            }
//
//            if (d > g) {
//                d = g;
//            }
//
//            if (e > g) {
//                e = g;
//            }
//
//            if (f > g) {
//                f = g;
//            }
//
//            this.xd = (int)(d * 8000.0);
//            this.yd = (int)(e * 8000.0);
//            this.zd = (int)(f * 8000.0);
//        }
//    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.entityId = data.readByte();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.data = data.readInt();
        if (this.data > 0) {
            this.xd = data.readShort();
            this.yd = data.readShort();
            this.zd = data.readShort();
        }
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeByte(this.entityId);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeInt(this.data);
        if (this.data > 0) {
            data.writeShort(this.xd);
            data.writeShort(this.yd);
            data.writeShort(this.zd);
        }
    }
}
