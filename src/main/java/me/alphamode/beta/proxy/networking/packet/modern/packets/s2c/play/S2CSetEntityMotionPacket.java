package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3d;

public record S2CSetEntityMotionPacket(int id, Vec3d movement) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CSetEntityMotionPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CSetEntityMotionPacket::id,
			Vec3d.LERP_CODEC,
			S2CSetEntityMotionPacket::movement,
			S2CSetEntityMotionPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_ENTITY_MOTION;
	}
}
