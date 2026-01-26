package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CSetHealthPacket(float health, int food, float saturation) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CSetHealthPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.FLOAT,
			S2CSetHealthPacket::health,
			ModernStreamCodecs.VAR_INT,
			S2CSetHealthPacket::food,
			BasicStreamCodecs.FLOAT,
			S2CSetHealthPacket::saturation,
			S2CSetHealthPacket::new
	);

	public S2CSetHealthPacket(final float health) {
		this(health, 3, 1.0F);
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_HEALTH;
	}
}
