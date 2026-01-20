package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TakeItemEntityPacket implements Packet {
    public int itemId;
    public int playerId;

    public TakeItemEntityPacket() {
    }

    public TakeItemEntityPacket(int itemId, int playerId) {
        this.itemId = itemId;
        this.playerId = playerId;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.itemId = data.readInt();
        this.playerId = data.readInt();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeInt(this.itemId);
        data.writeInt(this.playerId);
    }
}
