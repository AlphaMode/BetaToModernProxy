package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record GameEventPacket(byte event) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, GameEventPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.BYTE,
			GameEventPacket::event,
			GameEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.GAME_EVENT;
	}
}
