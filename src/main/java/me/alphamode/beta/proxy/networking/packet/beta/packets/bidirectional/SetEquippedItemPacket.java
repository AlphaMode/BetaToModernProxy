package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;

public record SetEquippedItemPacket(int entityId, short slot, BetaItemStack item) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, BetaItemStack> EQUIPPED_ITEM_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack item) {
			buf.writeShort(item.id());
			buf.writeShort(item.aux());
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			return new BetaItemStack(buf.readShort(), 1, buf.readShort());
		}
	};

	public static final StreamCodec<ByteBuf, SetEquippedItemPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			SetEquippedItemPacket::entityId,
			BasicStreamCodecs.SHORT,
			SetEquippedItemPacket::slot,
			EQUIPPED_ITEM_CODEC,
			SetEquippedItemPacket::item,
			SetEquippedItemPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_EQUIPPED_ITEM;
	}
}
