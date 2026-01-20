package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerSetDataPacket implements Packet {
    public int containerId;
    public int id;
    public int value;

    public ContainerSetDataPacket() {
    }

    public ContainerSetDataPacket(int containerId, int id, int value) {
        this.containerId = containerId;
        this.id = id;
        this.value = value;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readUnsignedByte();
        this.id = data.readShort();
        this.value = data.readShort();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
        data.writeShort(this.id);
        data.writeShort(this.value);
    }
}
