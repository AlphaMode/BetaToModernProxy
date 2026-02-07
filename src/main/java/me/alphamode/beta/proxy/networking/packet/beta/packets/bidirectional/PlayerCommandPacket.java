package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerCommandPacket(int id, Action action) implements BetaPacket {
	public static final StreamCodec<ByteStream, PlayerCommandPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			PlayerCommandPacket::id,
			BetaStreamCodecs.javaEnum(Action.class),
			PlayerCommandPacket::action,
			PlayerCommandPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.PLAYER_COMMAND;
	}

	public enum Action {
		NONE,
		CROUCH,
		UNCROUCH,
		WAKE_UP
	}
}
