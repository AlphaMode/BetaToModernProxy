package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.BetaEntityTypes;

public record AddEntityPacket(int entityId, BetaEntityTypes type, Vec3i position, int data, short xd, short yd,
							  short zd) implements BetaPacket {
	public static final StreamCodec<ByteStream, AddEntityPacket> CODEC = AbstractPacket.codec(AddEntityPacket::write, AddEntityPacket::new);

	public AddEntityPacket(final ByteStream buf) {
		final int id = buf.readInt();
		final BetaEntityTypes type = BetaEntityTypes.CODEC.decode(buf);
		final Vec3i position = Vec3i.CODEC.decode(buf);
		final int data = buf.readInt();

		short xd = 0;
		short yd = 0;
		short zd = 0;
		if (data > 0) {
			xd = buf.readShort();
			yd = buf.readShort();
			zd = buf.readShort();
		}

		this(id, type, position, data, xd, yd, zd);
	}

	public void write(final ByteStream buf) {
		buf.writeInt(this.entityId);
		BetaEntityTypes.CODEC.encode(buf, this.type);
		Vec3i.CODEC.encode(buf, this.position);
		buf.writeInt(this.data);
		if (this.data > 0) {
			buf.writeShort(this.xd);
			buf.writeShort(this.yd);
			buf.writeShort(this.zd);
		}
	}

	public Vec3d getPosition() {
		return this.position.toVec3d().divide(32.0);
	}

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.ADD_ENTITY;
	}
}
