package me.alphamode.beta.proxy.util.data.modern.item;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public interface HashedStack {
	HashedStack EMPTY = new HashedStack() {
		@Override
		public String toString() {
			return "<empty>";
		}

		@Override
		public boolean matches(ModernItemStack stack, HashedPatchMap.HashGenerator hasher) {
			return stack.isEmpty();
		}
	};

	StreamCodec<ByteBuf, HashedStack> CODEC = null;
	// TODO: StreamCodec<ByteBuf, HashedStack> STREAM_CODEC = ModernStreamCodecs.optional(HashedStack.ActualItem.STREAM_CODEC).map(actualItem -> (HashedStack) DataFixUtils.orElse(actualItem, EMPTY), hashedStack -> hashedStack instanceof HashedStack.ActualItem actualItem ? Optional.of(actualItem) : Optional.empty());

	boolean matches(ModernItemStack var1, HashedPatchMap.HashGenerator var2);

	static HashedStack create(ModernItemStack itemStack, HashedPatchMap.HashGenerator hasher) {
		return itemStack.isEmpty()
				? EMPTY
				: new ActualItem(itemStack.itemId(), itemStack.count(), HashedPatchMap.create(itemStack.components(), hasher));
	}

	record ActualItem(int itemId, int count, HashedPatchMap components) implements HashedStack {
		// TODO
		/*public static final StreamCodec<ByteBuf, HashedStack.ActualItem> STREAM_CODEC = StreamCodec.composite(
				ModernStreamCodecs.holderRegistry(Registries.ITEM),
				HashedStack.ActualItem::item,
				ModernStreamCodecs.VAR_INT,
				HashedStack.ActualItem::count,
				HashedPatchMap.STREAM_CODEC,
				HashedStack.ActualItem::components,
				HashedStack.ActualItem::new
		);*/

		@Override
		public boolean matches(ModernItemStack itemStack, HashedPatchMap.HashGenerator hasher) {
			if (this.count != itemStack.count()) {
				return false;
			} else {
				return this.itemId == itemStack.itemId() && this.components.matches(itemStack.components(), hasher);
			}
		}
	}
}
