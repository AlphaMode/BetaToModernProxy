package me.alphamode.beta.proxy.util.data.beta.item;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BetaItemStack(short itemId, byte count, short aux) {
	public static final BetaItemStack EMPTY = new BetaItemStack((short) 0, (byte) 0, (short) 0);

	public static final StreamCodec<ByteStream, BetaItemStack> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final BetaItemStack stack) {
			stream.writeShort(stack.itemId);
			stream.writeByte(stack.count);
			stream.writeShort(stack.aux);
		}

		@Override
		public BetaItemStack decode(final ByteStream stream) {
			final short id = stream.readShort();
			return new BetaItemStack(id, stream.readByte(), stream.readShort());
		}
	};

	public static final StreamCodec<ByteStream, BetaItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final BetaItemStack stack) {
			if (stack == null) {
				stream.writeShort((short) -1);
			} else {
				stream.writeShort(stack.itemId);
				stream.writeByte(stack.count);
				stream.writeShort(stack.aux);
			}
		}

		@Override
		public BetaItemStack decode(final ByteStream stream) {
			final short id = stream.readShort();
			if (id < 0) {
				return null;
			} else {
				return new BetaItemStack(id, stream.readByte(), stream.readShort());
			}
		}
	};

	public static final StreamCodec<ByteStream, BetaItemStack> NO_DATA_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final BetaItemStack stack) {
			stream.writeShort(stack.itemId);
			stream.writeShort(stack.aux());
		}

		@Override
		public BetaItemStack decode(final ByteStream stream) {
			return new BetaItemStack(stream.readShort(), (byte) 1, stream.readShort());
		}
	};

	public boolean isEmpty() {
		return this.itemId == 0 || this.count <= 0;
	}
}
