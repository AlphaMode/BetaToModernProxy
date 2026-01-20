package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetHealthPacket implements Packet {
    public int health;

    public SetHealthPacket() {
    }

    public SetHealthPacket(int health) {
        this.health = health;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.health = data.readShort();
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        data.writeShort(this.health);
    }
}
