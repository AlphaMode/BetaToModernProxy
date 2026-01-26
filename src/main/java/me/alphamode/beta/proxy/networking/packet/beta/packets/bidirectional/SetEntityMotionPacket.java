package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetEntityMotionPacket(int id, short deltaX, short deltaY, short deltaZ) implements BetaPacket {
	public static final double DELTA_CAP = 3.9;
	public static final StreamCodec<ByteBuf, SetEntityMotionPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			SetEntityMotionPacket::id,
			BasicStreamCodecs.SHORT,
			SetEntityMotionPacket::deltaX,
			BasicStreamCodecs.SHORT,
			SetEntityMotionPacket::deltaY,
			BasicStreamCodecs.SHORT,
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
