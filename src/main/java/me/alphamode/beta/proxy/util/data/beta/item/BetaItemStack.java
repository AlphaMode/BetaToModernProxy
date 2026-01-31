package me.alphamode.beta.proxy.util.data.beta.item;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BetaItemStack(int itemId, int count, int aux) {
	public static final BetaItemStack EMPTY = new BetaItemStack(0, 0, 0);

	public static final StreamCodec<ByteBuf, BetaItemStack> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack stack) {
			buf.writeShort(stack.itemId);
			buf.writeByte(stack.count);
			buf.writeShort(stack.aux);
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			final int id = buf.readShort();
			return new BetaItemStack(id, buf.readByte(), buf.readShort());
		}
	};

	public static final StreamCodec<ByteBuf, BetaItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack stack) {
			if (stack == null) {
				buf.writeShort(-1);
			} else {
				buf.writeShort(stack.itemId);
				buf.writeByte(stack.count);
				buf.writeShort(stack.aux);
			}
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			final int id = buf.readShort();
			if (id < 0) {
				return null;
			} else {
				return new BetaItemStack(id, buf.readByte(), buf.readShort());
			}
		}
	};

	public static final StreamCodec<ByteBuf, BetaItemStack> NO_DATA_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final BetaItemStack stack) {
			buf.writeShort(stack.itemId);
			buf.writeShort(stack.aux());
		}

		@Override
		public BetaItemStack decode(final ByteBuf buf) {
			final int id = buf.readShort();
			return new BetaItemStack(id, 1, buf.readShort());
		}
	};
}
