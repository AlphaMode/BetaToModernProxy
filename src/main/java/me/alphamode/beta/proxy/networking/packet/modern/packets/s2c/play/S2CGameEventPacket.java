package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CGameEventPacket(EventType type, float value) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CGameEventPacket> CODEC = StreamCodec.composite(
			BetaStreamCodecs.javaEnum(EventType.class), // TODO/NOTE: using beta stream codec cuz it reads a byte
			S2CGameEventPacket::type,
			CommonStreamCodecs.FLOAT,
			S2CGameEventPacket::value,
			S2CGameEventPacket::new
	);

	public float getValue() {
		return this.value;
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.GAME_EVENT;
	}

	public enum EventType {
		NO_RESPAWN_BLOCK_AVAILABLE,
		START_RAINING,
		STOP_RAINING,
		CHANGE_GAME_MODE,
		WIN_GAME,
		DEMO_EVENT,
		PLAY_ARROW_HIT_SOUND,
		RAIN_LEVEL_CHANGE,
		THUNDER_LEVEL_CHANGE,
		PUFFER_FISH_STING,
		GUARDIAN_ELDER_EFFECT,
		IMMEDIATE_RESPAWN,
		LIMITED_CRAFTING,
		LEVEL_CHUNKS_LOAD_START
	}
}
