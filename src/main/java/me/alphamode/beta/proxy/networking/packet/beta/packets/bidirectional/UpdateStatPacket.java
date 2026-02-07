package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record UpdateStatPacket(int id, byte amount) implements BetaPacket {
	public static final StreamCodec<ByteStream, UpdateStatPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			UpdateStatPacket::id,
			CommonStreamCodecs.BYTE,
			UpdateStatPacket::amount,
			UpdateStatPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.UPDATE_STAT;
	}
}
