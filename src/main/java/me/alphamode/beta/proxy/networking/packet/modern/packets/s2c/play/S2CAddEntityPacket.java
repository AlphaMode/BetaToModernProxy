package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;

import java.util.UUID;

public record S2CAddEntityPacket(
		int entityId,
		UUID uuid,
		int type,
		Vec3d position,
		Vec3d velocity,
		byte pitch,
		byte angle,
		byte headYaw,
		int data
) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CAddEntityPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CAddEntityPacket::entityId,
			ModernStreamCodecs.UUID,
			S2CAddEntityPacket::uuid,
			ModernStreamCodecs.VAR_INT,
			S2CAddEntityPacket::type,
			Vec3d.CODEC,
			S2CAddEntityPacket::position,
			Vec3d.LERP_CODEC,
			S2CAddEntityPacket::velocity,
			BasicStreamCodecs.BYTE,
			S2CAddEntityPacket::pitch,
			BasicStreamCodecs.BYTE,
			S2CAddEntityPacket::angle,
			BasicStreamCodecs.BYTE,
			S2CAddEntityPacket::headYaw,
			ModernStreamCodecs.VAR_INT,
			S2CAddEntityPacket::data,
			S2CAddEntityPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.ADD_ENTITY;
	}
}
