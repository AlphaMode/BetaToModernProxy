package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class InteractPacket implements Packet {
	public int source;
	public int target;
	public int action;

	public InteractPacket() {
	}

	public InteractPacket(final int playerId, final int entityId, final int action) {
		this.source = playerId;
		this.target = entityId;
		this.action = action;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.source = buf.readInt();
		this.target = buf.readInt();
		this.action = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.source);
		buf.writeInt(this.target);
		buf.writeByte(this.action);
	}
}
