package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.entity.SynchedEntityData;
import net.raphimc.netminecraft.packet.Packet;

import java.util.List;

public class AddMobPacket implements Packet {
	public int id;
	public byte type;
	public Vec3i position;
	public byte yRot;
	public byte xRot;
	private List<SynchedEntityData.DataItem<?>> dataItems;

	public AddMobPacket() {
	}

	public AddMobPacket(final int id, final byte type, final int x, final int y, final int z, final byte yRot, final byte xRot, final List<SynchedEntityData.DataItem<?>> dataItems) {
		this.id = id;
		this.type = type;
		this.position = new Vec3i(
				x, // Mth.floor(entity.x * 32.0); Vanilla
				y, // Mth.floor(entity.y * 32.0); Vanilla
				z  // Mth.floor(entity.z * 32.0); Vanilla
		);
		this.yRot = yRot; //(byte)(entity.yRot * 256.0F / 360.0F);
		this.xRot = xRot; //(byte)(entity.xRot * 256.0F / 360.0F);
		this.dataItems = dataItems;
	}

	@Override
	public void read(final ByteBuf buf, final int protocolVersion) {
		this.id = buf.readInt();
		this.type = buf.readByte();
		this.position = Vec3i.CODEC.decode(buf);
		this.yRot = buf.readByte();
		this.xRot = buf.readByte();
		this.dataItems = SynchedEntityData.unpack(buf);
	}

	@Override
	public void write(final ByteBuf buf, final int protocolVersion) {
		buf.writeInt(this.id);
		buf.writeByte(this.type);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeByte(this.yRot);
		buf.writeByte(this.xRot);
		SynchedEntityData.pack(buf, this.dataItems);
	}
}
