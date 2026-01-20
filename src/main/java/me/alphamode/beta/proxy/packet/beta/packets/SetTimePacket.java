package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetTimePacket implements Packet {
    public long time;

    public SetTimePacket() {
    }

    public SetTimePacket(long time) {
        this.time = time;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.time = data.readLong();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeLong(this.time);
    }
}
