package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class EntityEventPacket implements Packet {
    public int entityId;
    public byte eventId;

    public EntityEventPacket() {
    }

    public EntityEventPacket(int entityId, byte eventId) {
        this.entityId = entityId;
        this.eventId = eventId;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.entityId = data.readInt();
        this.eventId = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.entityId);
        data.writeByte(this.eventId);
    }
}
