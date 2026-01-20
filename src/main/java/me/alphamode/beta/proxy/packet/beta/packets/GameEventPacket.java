package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class GameEventPacket implements Packet {
    public static final String[] EVENT_LANGUAGE_ID = new String[]{"tile.bed.notValid", null, null};
    public int event;

    public GameEventPacket() {
    }

    public GameEventPacket(int event) {
        this.event = event;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.event = data.readByte();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeByte(this.event);
    }
}
