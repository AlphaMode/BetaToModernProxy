package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class PlayerChangeDimensionPacket implements Packet {
    public byte dimension;

    public PlayerChangeDimensionPacket() {
    }

    public PlayerChangeDimensionPacket(byte dimension) {
        this.dimension = dimension;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.dimension = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.dimension);
    }
}
