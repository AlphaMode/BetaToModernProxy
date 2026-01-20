package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class PreLoginPacket implements Packet {
    public String userName;

    public PreLoginPacket() {
    }

    public PreLoginPacket(String userName) {
        this.userName = userName;
    }

    @Override
    public void read(ByteBuf buf, int protocolVersion) {
        this.userName = PacketUtils.readString(buf, 32);
    }

    @Override
    public void write(ByteBuf buf, int protocolVersion) {
        PacketUtils.writeString(this.userName, buf);
    }
}
