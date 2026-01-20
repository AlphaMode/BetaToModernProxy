package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ChunkVisibilityPacket implements Packet {
    public int x;
    public int z;
    public boolean visible;

    public ChunkVisibilityPacket() {
//        this.shouldDelay = false;
    }

    public ChunkVisibilityPacket(int x, int z, boolean visible) {
//        this.shouldDelay = false;
        this.x = x;
        this.z = z;
        this.visible = (boolean)visible;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.z = data.readInt();
        this.visible = data.readByte() != 0;
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeInt(this.z);
        data.writeByte(this.visible ? 1 : 0);
    }
}
