package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class ChatPacket implements Packet {
    public String message;

    public ChatPacket() {
    }

    public ChatPacket(String message) {
        if (message.length() > 119) {
            message = message.substring(0, 119);
        }

        this.message = message;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.message = PacketUtils.readString(data, 119);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        PacketUtils.writeString(this.message, data);
    }
}
