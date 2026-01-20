package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.Packet;

public class SetEntityMotionPacket implements Packet {
	public static final double DELTA_CAP = 3.9;

	public int id;
	public int deltaX;
	public int deltaY;
	public int deltaZ;

	public SetEntityMotionPacket() {
	}

//    public SetEntityMotionPacket(Entity entity) {
//        this(entity.id, entity.xd, entity.yd, entity.zd);
//    }

	public SetEntityMotionPacket(final int id, final double xd, final double yd, final double zd) {
		this.id = id;
		this.deltaX = (int) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, xd)) * 8000.0);
		this.deltaY = (int) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, yd)) * 8000.0);
		this.deltaZ = (int) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, zd)) * 8000.0);
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.deltaX = buf.readShort();
		this.deltaY = buf.readShort();
		this.deltaZ = buf.readShort();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeShort(this.deltaX);
		buf.writeShort(this.deltaY);
		buf.writeShort(this.deltaZ);
	}
}
