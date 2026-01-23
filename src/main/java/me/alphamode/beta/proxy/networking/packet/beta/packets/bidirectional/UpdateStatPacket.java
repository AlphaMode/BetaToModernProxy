package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record UpdateStatPacket(int id, byte amount) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, UpdateStatPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			UpdateStatPacket::id,
			BasicStreamCodecs.BYTE,
			UpdateStatPacket::amount,
			UpdateStatPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.UPDATE_STAT;
	}
}
