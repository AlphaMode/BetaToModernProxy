package me.alphamode.beta.proxy.util.data.modern.item;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;

public record ModernItemStack(int itemId, int count, DataComponentPatch components) {
	public static final ModernItemStack EMPTY = new ModernItemStack(0, 0, DataComponentPatch.EMPTY);
	public static final StreamCodec<ByteStream, ModernItemStack> CODEC = null;

	public static final StreamCodec<ByteStream, ModernItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final ModernItemStack stack) {
			ModernStreamCodecs.VAR_INT.encode(buf, stack.count);
			if (stack.count > 0) {
				ModernStreamCodecs.VAR_INT.encode(buf, stack.itemId);
				DataComponentPatch.STREAM_CODEC.encode(buf, stack.components);
			}
		}

		@Override
		public ModernItemStack decode(final ByteStream buf) {
			final int count = ModernStreamCodecs.VAR_INT.decode(buf);
			if (count <= 0) {
				return ModernItemStack.EMPTY;
			} else {
				final int id = ModernStreamCodecs.VAR_INT.decode(buf);
				final DataComponentPatch components = DataComponentPatch.STREAM_CODEC.decode(buf);
				return new ModernItemStack(id, count, components);
			}
		}
	};

	public boolean isEmpty() {
		return this == EMPTY || this.itemId == 0 || this.count <= 0;
	}
}
