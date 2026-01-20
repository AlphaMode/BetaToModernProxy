package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class PlayerActionPacket implements Packet {
    public int x;
    public int y;
    public int z;
    public int face;
    public int action;

    public PlayerActionPacket() {
    }

    public PlayerActionPacket(int action, int x, int y, int z, int face) {
        this.action = action;
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = face;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.action = data.readByte();
        this.x = data.readInt();
        this.y = data.readByte();
        this.z = data.readInt();
        this.face = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.action);
        data.writeInt(this.x);
        data.writeByte(this.y);
        data.writeInt(this.z);
        data.writeByte(this.face);
    }
}
