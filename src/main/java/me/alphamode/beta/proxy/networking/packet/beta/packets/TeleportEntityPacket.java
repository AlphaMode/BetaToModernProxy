package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.Vec3i;
import net.raphimc.netminecraft.packet.Packet;

public class TeleportEntityPacket implements Packet {
	public int id;
	public Vec3i position;
	public byte yRot;
	public byte xRot;

	public TeleportEntityPacket() {
	}

//    public TeleportEntityPacket(Entity entity) {
//        this.id = entity.id;
//        this.x = Mth.floor(entity.x * 32.0);
//        this.y = Mth.floor(entity.y * 32.0);
//        this.z = Mth.floor(entity.z * 32.0);
//        this.yRot = (byte)(entity.yRot * 256.0F / 360.0F);
//        this.xRot = (byte)(entity.xRot * 256.0F / 360.0F);
//    }

	public TeleportEntityPacket(final int id, final Vec3i position, final byte yRot, final byte xRot) {
		this.id = id;
		this.position = position;
		this.yRot = yRot;
		this.xRot = xRot;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.position = Vec3i.CODEC.decode(buf);
		this.yRot = buf.readByte();
		this.xRot = buf.readByte();
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeByte(this.yRot);
		buf.writeByte(this.xRot);
	}
}
