package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TakeItemEntityPacket(int itemId, int playerId) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, TakeItemEntityPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			TakeItemEntityPacket::itemId,
			BasicCodecs.INT,
			TakeItemEntityPacket::playerId,
			TakeItemEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TAKE_ITEM_ENTITY;
	}
}
