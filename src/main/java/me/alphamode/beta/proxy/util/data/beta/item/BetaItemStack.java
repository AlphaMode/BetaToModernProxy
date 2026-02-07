package me.alphamode.beta.proxy.util.data.beta.item;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BetaItemStack(short itemId, byte count, short aux) {
	public static final BetaItemStack EMPTY = new BetaItemStack((short) 0, (byte) 0, (short) 0);

	public static final StreamCodec<ByteStream, BetaItemStack> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final BetaItemStack stack) {
			buf.writeShort(stack.itemId);
			buf.writeByte(stack.count);
			buf.writeShort(stack.aux);
		}

		@Override
		public BetaItemStack decode(final ByteStream buf) {
			final short id = buf.readShort();
			return new BetaItemStack(id, buf.readByte(), buf.readShort());
		}
	};

	public static final StreamCodec<ByteStream, BetaItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final BetaItemStack stack) {
			if (stack == null) {
				buf.writeShort((short) -1);
			} else {
				buf.writeShort(stack.itemId);
				buf.writeByte(stack.count);
				buf.writeShort(stack.aux);
			}
		}

		@Override
		public BetaItemStack decode(final ByteStream buf) {
			final short id = buf.readShort();
			if (id < 0) {
				return null;
			} else {
				return new BetaItemStack(id, buf.readByte(), buf.readShort());
			}
		}
	};

	public static final StreamCodec<ByteStream, BetaItemStack> NO_DATA_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final BetaItemStack stack) {
			buf.writeShort(stack.itemId);
			buf.writeShort(stack.aux());
		}

		@Override
		public BetaItemStack decode(final ByteStream buf) {
			return new BetaItemStack(buf.readShort(), (byte) 1, buf.readShort());
		}
	};

	public boolean isEmpty() {
		return this.itemId == 0 || this.count <= 0;
	}
}
