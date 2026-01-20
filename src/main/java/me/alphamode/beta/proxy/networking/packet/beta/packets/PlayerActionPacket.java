package me.alphamode.beta.proxy.networking.packet.beta.packets;

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
    public void read(final ByteBuf buf, final int protocolVersion) {
        this.action = buf.readByte();
        this.x = buf.readInt();
        this.y = buf.readByte();
        this.z = buf.readInt();
        this.face = buf.readByte();
    }

    @Override
    public void write(final ByteBuf buf, final int protocolVersion) {
        buf.writeByte(this.action);
        buf.writeInt(this.x);
        buf.writeByte(this.y);
        buf.writeInt(this.z);
        buf.writeByte(this.face);
    }
}
