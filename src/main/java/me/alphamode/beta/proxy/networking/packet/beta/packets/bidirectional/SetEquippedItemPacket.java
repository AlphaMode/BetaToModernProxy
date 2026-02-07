package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record SetEquippedItemPacket(int entityId, short slot, BetaItemStack item) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetEquippedItemPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			SetEquippedItemPacket::entityId,
			CommonStreamCodecs.SHORT,
			SetEquippedItemPacket::slot,
			BetaItemStack.NO_DATA_CODEC,
			SetEquippedItemPacket::item,
			SetEquippedItemPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_EQUIPPED_ITEM;
	}
}
