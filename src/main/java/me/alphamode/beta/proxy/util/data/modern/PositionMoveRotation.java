package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;

public record PositionMoveRotation(Vec3d position, Vec3d deltaMovement, float yRot, float xRot) {
	public static final StreamCodec<ByteStream, PositionMoveRotation> CODEC = StreamCodec.composite(
			Vec3d.CODEC,
			PositionMoveRotation::position,
			Vec3d.CODEC,
			PositionMoveRotation::deltaMovement,
			CommonStreamCodecs.FLOAT,
			PositionMoveRotation::yRot,
			CommonStreamCodecs.FLOAT,
			PositionMoveRotation::xRot,
			PositionMoveRotation::new
	);
}
