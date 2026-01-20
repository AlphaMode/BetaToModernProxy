package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;
import net.raphimc.netminecraft.packet.Packet;

public class DisconnectPacket implements Packet {
    public String reason;

    public DisconnectPacket() {
    }

    public DisconnectPacket(String reason) {
        this.reason = reason;
    }

    @Override
    public void read(ByteBuf data, int protocolVersion) {
        this.reason = PacketUtils.readString(data, 100);
    }

    @Override
    public void write(ByteBuf data, int protocolVersion) {
        PacketUtils.writeString(this.reason, data);
    }
}
