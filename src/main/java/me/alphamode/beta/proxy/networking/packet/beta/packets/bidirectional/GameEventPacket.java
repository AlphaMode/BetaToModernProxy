package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record GameEventPacket(Type type) implements BetaPacket {
	public static final StreamCodec<ByteBuf, GameEventPacket> CODEC = StreamCodec.composite(
			BetaStreamCodecs.javaEnum(Type.class),
			GameEventPacket::type,
			GameEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.GAME_EVENT;
	}

	public enum Type {
		INVALID_BED,
		BEGIN_RAINING,
		END_RAINING
	}
}
