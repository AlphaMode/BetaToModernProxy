package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CAnimatePacket(int entityId, Action action) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CAnimatePacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CAnimatePacket::entityId,
			BetaStreamCodecs.javaEnum(Action.class), // TODO/NOTE: Use beta codec cuz it reads byte
			S2CAnimatePacket::action,
			S2CAnimatePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.ANIMATE;
	}

	public enum Action {
		SWING_MAIN_HAND,
		UNUSED,
		WAKE_UP,
		SWING_OFF_HAND,
		CRITICAL_HIT,
		MAGIC_CRITICAL_HIT
	}
}
