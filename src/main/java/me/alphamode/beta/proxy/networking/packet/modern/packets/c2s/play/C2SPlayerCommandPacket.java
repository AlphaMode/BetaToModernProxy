package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPlayerCommandPacket(int id, Action action, int data) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SPlayerCommandPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SPlayerCommandPacket::id,
			ModernStreamCodecs.javaEnum(Action.class),
			C2SPlayerCommandPacket::action,
			ModernStreamCodecs.VAR_INT,
			C2SPlayerCommandPacket::data,
			C2SPlayerCommandPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PLAYER_COMMAND;
	}

	public enum Action {
		STOP_SLEEPING,
		START_SPRINTING,
		STOP_SPRINTING,
		START_RIDING_JUMP,
		STOP_RIDING_JUMP,
		OPEN_INVENTORY,
		START_FALL_FLYING
	}
}
