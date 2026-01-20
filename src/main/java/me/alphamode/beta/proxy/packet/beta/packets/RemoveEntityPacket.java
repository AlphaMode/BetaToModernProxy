package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class RemoveEntityPacket implements Packet {
    public int id;

    public RemoveEntityPacket() {
    }

    public RemoveEntityPacket(int entityId) {
        this.id = entityId;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.id = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.id);
    }
}
