package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;

public record ContainerSetContentPacket(byte containerId, BetaItemStack[] items) implements BetaPacket {
	public static final StreamCodec<ByteBuf, BetaItemStack[]> ITEM_STACK_ARRAY = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack[] items) {
			buf.writeShort(items.length);
			for (final BetaItemStack item : items) {
				BetaItemStack.OPTIONAL_CODEC.encode(buf, item);
			}
		}

		@Override
		public BetaItemStack[] decode(final ByteBuf buf) {
			final int size = buf.readShort();

			final BetaItemStack[] items = new BetaItemStack[size];
			for (int i = 0; i < size; i++) {
				items[i] = BetaItemStack.OPTIONAL_CODEC.decode(buf);
			}

			return items;
		}
	};

	public static final StreamCodec<ByteBuf, ContainerSetContentPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			ContainerSetContentPacket::containerId,
			ITEM_STACK_ARRAY,
			ContainerSetContentPacket::items,
			ContainerSetContentPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_SET_CONTENT;
	}
}
