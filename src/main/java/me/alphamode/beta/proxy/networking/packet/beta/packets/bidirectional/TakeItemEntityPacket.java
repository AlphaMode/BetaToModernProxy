package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record TakeItemEntityPacket(int itemId, int playerId) implements BetaPacket {
	public static final StreamCodec<ByteBuf, TakeItemEntityPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			TakeItemEntityPacket::itemId,
			BasicStreamCodecs.INT,
			TakeItemEntityPacket::playerId,
			TakeItemEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.TAKE_ITEM_ENTITY;
	}
}
