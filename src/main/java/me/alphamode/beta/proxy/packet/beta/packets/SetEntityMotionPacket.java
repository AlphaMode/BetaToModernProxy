package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetEntityMotionPacket implements Packet {
    public int id;
    public int xa;
    public int ya;
    public int za;

    public SetEntityMotionPacket() {
    }

//    public SetEntityMotionPacket(Entity entity) {
//        this(entity.id, entity.xd, entity.yd, entity.zd);
//    }

    public SetEntityMotionPacket(int id, double xd, double yd, double zd) {
        this.id = id;
        double d = 3.9;
        if (xd < -d) {
            xd = -d;
        }

        if (yd < -d) {
            yd = -d;
        }

        if (zd < -d) {
            zd = -d;
        }

        if (xd > d) {
            xd = d;
        }

        if (yd > d) {
            yd = d;
        }

        if (zd > d) {
            zd = d;
        }

        this.xa = (int)(xd * 8000.0);
        this.ya = (int)(yd * 8000.0);
        this.za = (int)(zd * 8000.0);
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.xa = data.readShort();
        this.ya = data.readShort();
        this.za = data.readShort();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeShort(this.xa);
        data.writeShort(this.ya);
        data.writeShort(this.za);
    }
}
