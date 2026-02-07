package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetCarriedItemPacket(short slot) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetCarriedItemPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.SHORT,
			SetCarriedItemPacket::slot,
			SetCarriedItemPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_CARRIED_ITEM;
	}
}
