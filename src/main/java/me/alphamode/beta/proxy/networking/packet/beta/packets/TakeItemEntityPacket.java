package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class TakeItemEntityPacket implements Packet {
	public int itemId;
	public int playerId;

	public TakeItemEntityPacket() {
	}

	public TakeItemEntityPacket(final int itemId, final int playerId) {
		this.itemId = itemId;
		this.playerId = playerId;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.itemId = buf.readInt();
		this.playerId = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.itemId);
		buf.writeInt(this.playerId);
	}
}
