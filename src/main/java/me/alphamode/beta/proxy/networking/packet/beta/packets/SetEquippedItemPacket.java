package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BetaItemStack;

public record SetEquippedItemPacket(int entityId, short slot, BetaItemStack item) implements RecordPacket {
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
			ByteBufCodecs.INT,
			SetEquippedItemPacket::entityId,
			ByteBufCodecs.SHORT,
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
