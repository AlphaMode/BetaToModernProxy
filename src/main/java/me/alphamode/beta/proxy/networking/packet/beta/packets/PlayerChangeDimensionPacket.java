package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerChangeDimensionPacket(byte dimension) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, PlayerChangeDimensionPacket> CODEC = StreamCodec.composite(
			BasicCodecs.BYTE,
			PlayerChangeDimensionPacket::dimension,
			PlayerChangeDimensionPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_CHANGE_DIMENSION;
	}
}
