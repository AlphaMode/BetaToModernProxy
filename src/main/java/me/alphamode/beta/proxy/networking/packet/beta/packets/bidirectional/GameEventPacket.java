package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record GameEventPacket(byte event) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, GameEventPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			GameEventPacket::event,
			GameEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.GAME_EVENT;
	}
}
