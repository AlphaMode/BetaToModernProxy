package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class InteractPacket implements Packet {
    public int source;
    public int target;
    public int action;

    public InteractPacket() {
    }

    public InteractPacket(int playerId, int entityId, int action) {
        this.source = playerId;
        this.target = entityId;
        this.action = action;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.source = data.readInt();
        this.target = data.readInt();
        this.action = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.source);
        data.writeInt(this.target);
        data.writeByte(this.action);
    }
}
