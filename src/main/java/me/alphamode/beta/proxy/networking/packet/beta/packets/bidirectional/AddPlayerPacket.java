package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPlayerPacket(int entityId, String name, Vec3i position, byte packedYRot, byte packedXRot,
							  short carriedItem) implements BetaPacket {
	public static final int MAX_NAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> NAME_CODEC = BetaStreamCodecs.stringUtf8(MAX_NAME_LENGTH);
	public static final StreamCodec<ByteBuf, AddPlayerPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AddPlayerPacket::entityId,
			NAME_CODEC,
			AddPlayerPacket::name,
			Vec3i.CODEC,
			AddPlayerPacket::position,
			BasicStreamCodecs.BYTE,
			AddPlayerPacket::packedYRot,
			BasicStreamCodecs.BYTE,
			AddPlayerPacket::packedXRot,
			BasicStreamCodecs.SHORT,
			AddPlayerPacket::carriedItem,
			AddPlayerPacket::new
	);

	public Vec3d getPosition() {
		return this.position.toVec3d().divide(32.0);
	}

	public float getYRot() {
		return Mth.unpackDegrees(this.packedYRot);
	}

	public float getXRot() {
		return Mth.unpackDegrees(this.packedXRot);
	}

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.ADD_PLAYER;
	}
}
