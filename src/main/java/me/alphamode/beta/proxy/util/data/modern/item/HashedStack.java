package me.alphamode.beta.proxy.util.data.modern.item;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.Optional;

public interface HashedStack {
	HashedStack EMPTY = new HashedStack() {
		@Override
		public String toString() {
			return "<empty>";
		}
	};

	StreamCodec<ByteStream, HashedStack> STREAM_CODEC = ModernStreamCodecs.optional(ActualItem.STREAM_CODEC).map(actualItem -> {
		if (actualItem.isEmpty()) {
			return EMPTY;
		} else {
			return actualItem.get();
		}
	}, hashedStack -> hashedStack instanceof ActualItem actualItem ? Optional.of(actualItem) : Optional.empty());

	static HashedStack create(ModernItemStack itemStack, HashedPatchMap.HashGenerator hasher) {
		return itemStack.isEmpty()
				? EMPTY
				: new ActualItem(itemStack.itemId(), itemStack.count(), HashedPatchMap.create(itemStack.components(), hasher));
	}

	record ActualItem(int itemId, int count, HashedPatchMap components) implements HashedStack {
		public static final StreamCodec<ByteStream, ActualItem> STREAM_CODEC = StreamCodec.composite(
				ModernStreamCodecs.VAR_INT,
				ActualItem::itemId,
				ModernStreamCodecs.VAR_INT,
				ActualItem::count,
				HashedPatchMap.STREAM_CODEC,
				ActualItem::components,
				ActualItem::new
		);
	}
}
