package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerChangeDimensionPacket(byte dimension) implements BetaPacket {
	public static final StreamCodec<ByteStream, PlayerChangeDimensionPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.BYTE,
			PlayerChangeDimensionPacket::dimension,
			PlayerChangeDimensionPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.PLAYER_CHANGE_DIMENSION;
	}
}
