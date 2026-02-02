package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerCommandPacket(int id, Action action) implements BetaPacket {
	public static final StreamCodec<ByteBuf, PlayerCommandPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			PlayerCommandPacket::id,
			BetaStreamCodecs.javaEnum(Action.class),
			PlayerCommandPacket::action,
			PlayerCommandPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_COMMAND;
	}

	public enum Action {
		NONE,
		CROUCH,
		UNCROUCH,
		WAKE_UP;
	}
}
