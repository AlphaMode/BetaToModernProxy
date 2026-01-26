package me.alphamode.beta.proxy.util.data.beta;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Item;

public record BetaItemStack(Item item, int count, int aux) {
	public static final StreamCodec<ByteBuf, BetaItemStack> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack stack) {
			buf.writeShort(BetaItems.getId(stack.item));
			buf.writeByte(stack.count);
			buf.writeShort(stack.aux);
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			return new BetaItemStack(BetaItems.byId(buf.readShort()), buf.readByte(), buf.readShort());
		}
	};

	public static final StreamCodec<ByteBuf, BetaItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack stack) {
			if (stack == null) {
				buf.writeShort(-1);
			} else {
				buf.writeShort(BetaItems.getId(stack.item));
				buf.writeByte(stack.count);
				buf.writeShort(stack.aux);
			}
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			final int id = buf.readShort();
			return id < 0 ? null : new BetaItemStack(BetaItems.byId(id), buf.readByte(), buf.readShort());
		}
	};

	public static final StreamCodec<ByteBuf, BetaItemStack> NO_DATA_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack stack) {
			buf.writeShort(BetaItems.getId(stack.item()));
			buf.writeShort(stack.aux());
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			return new BetaItemStack(BetaItems.byId(buf.readShort()), 1, buf.readShort());
		}
	};
}
