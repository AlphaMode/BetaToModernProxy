package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record GameEventPacket(byte event) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, GameEventPacket> CODEC = StreamCodec.composite(
			BasicCodecs.BYTE,
			GameEventPacket::event,
			GameEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.GAME_EVENT;
	}
}
