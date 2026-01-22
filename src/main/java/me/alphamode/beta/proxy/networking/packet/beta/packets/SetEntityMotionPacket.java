package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetEntityMotionPacket(int id, short deltaX, short deltaY, short deltaZ) implements BetaRecordPacket {
	public static final double DELTA_CAP = 3.9;
	public static final StreamCodec<ByteBuf, SetEntityMotionPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			SetEntityMotionPacket::id,
			ByteBufCodecs.SHORT,
			SetEntityMotionPacket::deltaX,
			ByteBufCodecs.SHORT,
			SetEntityMotionPacket::deltaY,
			ByteBufCodecs.SHORT,
			SetEntityMotionPacket::deltaZ,
			SetEntityMotionPacket::new
	);

//    public SetEntityMotionPacket(Entity entity) {
//        this(entity.entityId, entity.xd, entity.yd, entity.zd);
//    }

	public SetEntityMotionPacket(final int id, final double xd, final double yd, final double zd) {
		this(id, (short) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, xd)) * 8000.0), (short) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, yd)) * 8000.0), (short) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, zd)) * 8000.0));
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_ENTITY_MOTION;
	}
}
