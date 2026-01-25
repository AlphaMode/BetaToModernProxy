package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;

public record PositionMoveRotation(Vec3d position, Vec3d deltaMovement, float yRot, float xRot) {
	public static final StreamCodec<ByteBuf, PositionMoveRotation> CODEC = StreamCodec.composite(
			Vec3d.CODEC,
			PositionMoveRotation::position,
			Vec3d.CODEC,
			PositionMoveRotation::deltaMovement,
			BasicStreamCodecs.FLOAT,
			PositionMoveRotation::yRot,
			BasicStreamCodecs.FLOAT,
			PositionMoveRotation::xRot,
			PositionMoveRotation::new
	);
}
