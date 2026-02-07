package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record ContainerSetContentPacket(byte containerId, BetaItemStack[] items) implements BetaPacket {
	public static final StreamCodec<ByteStream, BetaItemStack[]> ITEM_STACK_ARRAY = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final BetaItemStack[] items) {
			buf.writeShort((short) items.length);
			for (final BetaItemStack item : items) {
				BetaItemStack.OPTIONAL_CODEC.encode(buf, item);
			}
		}

		@Override
		public BetaItemStack[] decode(final ByteStream buf) {
			final int size = buf.readShort();

			final BetaItemStack[] items = new BetaItemStack[size];
			for (int i = 0; i < size; i++) {
				items[i] = BetaItemStack.OPTIONAL_CODEC.decode(buf);
			}

			return items;
		}
	};

	public static final StreamCodec<ByteStream, ContainerSetContentPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.BYTE,
			ContainerSetContentPacket::containerId,
			ITEM_STACK_ARRAY,
			ContainerSetContentPacket::items,
			ContainerSetContentPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_SET_CONTENT;
	}
}
