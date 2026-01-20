package me.alphamode.beta.proxy.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class KeepAlivePacket implements Packet {
    @Override
    public void read(ByteBuf byteBuf, int protocolVersion) {}

    @Override
    public void write(ByteBuf byteBuf, int protocolVersion) {}
}
