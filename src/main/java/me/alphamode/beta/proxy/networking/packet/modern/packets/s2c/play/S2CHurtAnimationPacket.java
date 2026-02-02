package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CHurtAnimationPacket(int entityId, float yaw) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CHurtAnimationPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CHurtAnimationPacket::entityId,
			BasicStreamCodecs.FLOAT,
			S2CHurtAnimationPacket::yaw,
			S2CHurtAnimationPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.HURT_ANIMATION;
	}
}
