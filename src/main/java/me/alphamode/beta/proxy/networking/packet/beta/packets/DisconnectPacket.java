package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.ByteBufCodecs;
import net.raphimc.netminecraft.packet.Packet;

public class DisconnectPacket implements Packet {
	public String reason;

	public DisconnectPacket() {
	}

	public DisconnectPacket(String reason) {
		this.reason = reason;
	}

	@Override
	public void read(final ByteBuf data, final int protocolVersion) {
		ByteBufCodecs.stringUtf8(100).decode(data);
	}

	@Override
	public void write(final ByteBuf data, final int protocolVersion) {
		ByteBufCodecs.stringUtf8(100).encode(data, this.reason);
	}
}
