package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerChangeDimensionPacket(byte dimension) implements BetaPacket {
	public static final StreamCodec<ByteBuf, PlayerChangeDimensionPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			PlayerChangeDimensionPacket::dimension,
			PlayerChangeDimensionPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_CHANGE_DIMENSION;
	}
}
