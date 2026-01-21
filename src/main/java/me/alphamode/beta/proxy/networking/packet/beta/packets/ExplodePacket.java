package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record ExplodePacket(Vec3d pos, float radius, Vec3i[] toBlow) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, ExplodePacket> CODEC = RecordPacket.codec(ExplodePacket::write, ExplodePacket::new);

	public ExplodePacket(final ByteBuf buf) {
		Vec3d pos = Vec3d.CODEC.decode(buf);
		float radius = buf.readFloat();

		final int size = buf.readInt();
		Vec3i[] toBlow = new Vec3i[size];

		final Vec3i origin = pos.toVec3i();
		for (int i = 0; i < size; i++) {
			toBlow[i] = Vec3i.relative(origin).decode(buf);
		}
		this(pos, radius, toBlow);
	}

	public void write(final ByteBuf buf) {
		Vec3d.CODEC.encode(buf, this.pos);
		buf.writeFloat(this.radius);
		buf.writeInt(this.toBlow.length);

		final StreamCodec<ByteBuf, Vec3i> codec = Vec3i.relative(this.pos.toVec3i());
		for (final Vec3i tilePos : this.toBlow) {
			codec.encode(buf, tilePos);
		}
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.EXPLODE;
	}
}
