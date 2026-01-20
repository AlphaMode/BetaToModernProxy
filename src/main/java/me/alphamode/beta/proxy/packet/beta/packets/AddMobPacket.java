package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.entity.SynchedEntityData;
import net.raphimc.netminecraft.packet.Packet;

import java.util.List;

public class AddMobPacket implements Packet {
    public int id;
    public byte type;
    public int x;
    public int y;
    public int z;
    public byte yRot;
    public byte xRot;
    private List<SynchedEntityData.DataItem<?>> dataItems;

    public AddMobPacket() {
    }

    public AddMobPacket(int id, byte type, int x, int y, int z, byte yRot, byte xRot, List<SynchedEntityData.DataItem<?>> dataItems) {
        this.id = id;
        this.type = type;
        this.x = x; // Mth.floor(entity.x * 32.0); Vanilla
        this.y = y; // Mth.floor(entity.y * 32.0); Vanilla
        this.z = z; // Mth.floor(entity.z * 32.0); Vanilla
        this.yRot = yRot;//(byte)(entity.yRot * 256.0F / 360.0F);
        this.xRot = xRot;//(byte)(entity.xRot * 256.0F / 360.0F);
        this.dataItems = dataItems;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
        this.type = data.readByte();
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
        this.yRot = data.readByte();
        this.xRot = data.readByte();
        this.dataItems = SynchedEntityData.unpack(data);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
        data.writeByte(this.type);
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
        data.writeByte(this.yRot);
        data.writeByte(this.xRot);
        SynchedEntityData.pack(data, this.dataItems);
    }
}
