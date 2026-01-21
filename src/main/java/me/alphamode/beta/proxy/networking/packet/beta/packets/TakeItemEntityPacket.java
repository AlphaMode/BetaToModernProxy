package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TakeItemEntityPacket(int itemId, int playerId) implements RecordPacket {
	public static final StreamCodec<ByteBuf, TakeItemEntityPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			TakeItemEntityPacket::itemId,
			ByteBufCodecs.INT,
			TakeItemEntityPacket::playerId,
			TakeItemEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TAKE_ITEM_ENTITY;
	}
}
