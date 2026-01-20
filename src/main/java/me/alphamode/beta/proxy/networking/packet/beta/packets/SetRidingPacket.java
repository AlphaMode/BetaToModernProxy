package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetRidingPacket implements Packet {
	public int riderId;
	public int riddenId;

	public SetRidingPacket() {
	}

//    public SetRidingPacket(Entity passenger, Entity vehicle) {
//        this.riderId = passenger.id;
//        this.riddenId = vehicle != null ? vehicle.id : -1;
//    }

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.riderId = buf.readInt();
		this.riddenId = buf.readInt();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.riderId);
		buf.writeInt(this.riddenId);
	}
}
