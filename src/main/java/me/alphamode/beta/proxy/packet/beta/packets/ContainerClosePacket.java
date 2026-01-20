package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerClosePacket implements Packet {
    public int containerId;

    public ContainerClosePacket() {
    }

    public ContainerClosePacket(int containerId) {
        this.containerId = containerId;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
    }
}
