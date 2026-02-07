package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.modern.ModernEntityTypes;

import java.util.UUID;

public record S2CAddEntityPacket(
		int entityId,
		UUID uuid,
		ModernEntityTypes type,
		Vec3d position,
		Vec3d movement,
		byte xRot,
		byte yRot,
		byte yHeadRot,
		int data
) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CAddEntityPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CAddEntityPacket::entityId,
			ModernStreamCodecs.UUID,
			S2CAddEntityPacket::uuid,
			ModernEntityTypes.CODEC,
			S2CAddEntityPacket::type,
			Vec3d.CODEC,
			S2CAddEntityPacket::position,
			Vec3d.LERP_CODEC,
			S2CAddEntityPacket::movement,
			CommonStreamCodecs.BYTE,
			S2CAddEntityPacket::xRot,
			CommonStreamCodecs.BYTE,
			S2CAddEntityPacket::yRot,
			CommonStreamCodecs.BYTE,
			S2CAddEntityPacket::yHeadRot,
			ModernStreamCodecs.VAR_INT,
			S2CAddEntityPacket::data,
			S2CAddEntityPacket::new
	);

	public S2CAddEntityPacket(
			final int id,
			final UUID uuid,
			final ModernEntityTypes type,
			final Vec3d position,
			final Vec3d movement,
			final float xRot,
			final float yRot,
			final double yHeadRot,
			final int data
	) {
		this(id, uuid, type, position, movement, Mth.packDegrees(xRot), Mth.packDegrees(yRot), Mth.packDegrees((float) yHeadRot), data);
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.ADD_ENTITY;
	}
}
