package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record SetEquippedItemPacket(int entityId, short slot, BetaItemStack item) implements BetaPacket {
	public static final StreamCodec<ByteBuf, SetEquippedItemPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			SetEquippedItemPacket::entityId,
			BasicStreamCodecs.SHORT,
			SetEquippedItemPacket::slot,
			BetaItemStack.NO_DATA_CODEC,
			SetEquippedItemPacket::item,
			SetEquippedItemPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_EQUIPPED_ITEM;
	}
}
