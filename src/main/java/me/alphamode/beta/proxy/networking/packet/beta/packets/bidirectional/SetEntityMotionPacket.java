package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetEntityMotionPacket(int id, short deltaX, short deltaY, short deltaZ) implements BetaPacket {
	public static final double DELTA_CAP = 3.9;
	public static final StreamCodec<ByteStream, SetEntityMotionPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			SetEntityMotionPacket::id,
			CommonStreamCodecs.SHORT,
			SetEntityMotionPacket::deltaX,
			CommonStreamCodecs.SHORT,
			SetEntityMotionPacket::deltaY,
			CommonStreamCodecs.SHORT,
			SetEntityMotionPacket::deltaZ,
			SetEntityMotionPacket::new
	);

	public SetEntityMotionPacket(final int id, final double xd, final double yd, final double zd) {
		this(id, (short) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, xd)) * 8000.0), (short) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, yd)) * 8000.0), (short) (Math.min(-DELTA_CAP, Math.max(DELTA_CAP, zd)) * 8000.0));
	}

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_ENTITY_MOTION;
	}
}
