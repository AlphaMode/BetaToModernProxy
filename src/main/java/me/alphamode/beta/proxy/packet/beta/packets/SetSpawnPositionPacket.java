package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetSpawnPositionPacket implements Packet {
    public int x;
    public int y;
    public int z;

    public SetSpawnPositionPacket() {
    }

    public SetSpawnPositionPacket(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.x = data.readInt();
        this.y = data.readInt();
        this.z = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.x);
        data.writeInt(this.y);
        data.writeInt(this.z);
    }
}
