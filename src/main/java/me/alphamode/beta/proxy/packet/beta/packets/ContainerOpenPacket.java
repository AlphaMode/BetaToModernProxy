package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class ContainerOpenPacket implements Packet {
    public int containerId;
    public int type;
    public String title;
    public int size;

    public ContainerOpenPacket() {
    }

    public ContainerOpenPacket(int containerId, int id, String name, int size) {
        this.containerId = containerId;
        this.type = id;
        this.title = name;
        this.size = size;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.containerId = data.readUnsignedByte();
        this.type = data.readUnsignedByte();
        this.title = data.readUTF();
        this.size = data.readUnsignedByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.containerId);
        data.writeByte(this.type);
        data.writeUTF(this.title);
        data.writeByte(this.size);
    }
}
