package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerAckPacket implements Packet {
    public int containerId;
    public short uid;
    public boolean accepted;

    public ContainerAckPacket() {
    }

    public ContainerAckPacket(int containerId, short uid, boolean accepted) {
        this.containerId = containerId;
        this.uid = uid;
        this.accepted = accepted;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readUnsignedByte();
        this.uid = data.readShort();
        this.accepted = data.readUnsignedByte() != 0;
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
        data.writeShort(this.uid);
        data.writeByte(this.accepted ? 1 : 0);
    }
}
