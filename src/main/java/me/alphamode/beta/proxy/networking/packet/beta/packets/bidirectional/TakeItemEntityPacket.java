package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TakeItemEntityPacket(int itemId, int playerId) implements BetaPacket {
	public static final StreamCodec<ByteStream, TakeItemEntityPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			TakeItemEntityPacket::itemId,
			CommonStreamCodecs.INT,
			TakeItemEntityPacket::playerId,
			TakeItemEntityPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.TAKE_ITEM_ENTITY;
	}
}
