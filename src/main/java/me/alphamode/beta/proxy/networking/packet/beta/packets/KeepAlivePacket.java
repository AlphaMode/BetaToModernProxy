package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class KeepAlivePacket implements Packet {
	@Override
	public void read(final ByteBuf byteBuf, final int protocolVersion) {
	}

	@Override
	public void write(final ByteBuf byteBuf, final int protocolVersion) {
	}
}
