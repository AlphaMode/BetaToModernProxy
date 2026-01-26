package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Item;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;

import java.util.Optional;

public record ModernItemStack(Item item, int count, DataComponentPatch components) {
	public static final ModernItemStack EMPTY = new ModernItemStack(ModernItems.AIR, 0, DataComponentPatch.EMPTY);
	public static final StreamCodec<ByteBuf, ModernItemStack> CODEC = null;

	public static final StreamCodec<ByteBuf, ModernItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final ModernItemStack stack) {
			ModernStreamCodecs.VAR_INT.encode(buf, stack.count);
			if (stack.count > 0) {
				final int id = ModernItems.getRawId(stack.item);
				ModernStreamCodecs.optional(ModernStreamCodecs.VAR_INT).encode(buf, id == 0 ? Optional.empty() : Optional.of(id));
				ModernStreamCodecs.optional(DataComponentPatch.CODEC).encode(buf, stack.components == DataComponentPatch.EMPTY ? Optional.empty() : Optional.of(stack.components));
			}
		}

		@Override
		public ModernItemStack decode(final ByteBuf buf) {
			final int count = ModernStreamCodecs.VAR_INT.decode(buf);
			if (count <= 0) {
				return ModernItemStack.EMPTY;
			} else {
				final Optional<Integer> id = ModernStreamCodecs.optional(ModernStreamCodecs.VAR_INT).decode(buf);
				final Optional<DataComponentPatch> components = ModernStreamCodecs.optional(DataComponentPatch.CODEC).decode(buf);
				return new ModernItemStack(ModernItems.byRawId(id.orElse(0)), count, components.orElse(DataComponentPatch.EMPTY));
			}
		}
	};

	public boolean isEmpty() {
		return this == EMPTY || this.item == ModernItems.AIR || this.count <= 0;
	}
}
