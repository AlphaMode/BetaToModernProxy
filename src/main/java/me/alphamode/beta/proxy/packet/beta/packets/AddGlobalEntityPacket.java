package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class AddGlobalEntityPacket implements Packet {
    public int id;
    public int x;
    public int y;
    public int z;
    public int type;

    public AddGlobalEntityPacket() {
    }

//    public AddGlobalEntityPacket(Entity entitu) {
//        this.id = entitu.id;
//        this.x = Mth.floor(entitu.x * 32.0);
//        this.y = Mth.floor(entitu.y * 32.0);
//        this.z = Mth.floor(entitu.z * 32.0);
//        if (entitu instanceof LightningBolt) {
//            this.type = 1;
//        }
//    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.type = data.readByte();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeByte(this.type);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
    }
}
