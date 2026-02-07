package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CHurtAnimationPacket(int entityId, float yaw) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CHurtAnimationPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CHurtAnimationPacket::entityId,
			CommonStreamCodecs.FLOAT,
			S2CHurtAnimationPacket::yaw,
			S2CHurtAnimationPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.HURT_ANIMATION;
	}
}
