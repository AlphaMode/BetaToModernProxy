package me.alphamode.beta.proxy.util.data.modern.item;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentType;
import me.alphamode.beta.proxy.util.data.modern.components.TypedDataComponent;

import java.util.*;

public record HashedPatchMap(Map<DataComponentType<?>, Integer> addedComponents,
							 Set<DataComponentType<?>> removedComponents) {
	public static final StreamCodec<ByteStream, HashedPatchMap> STREAM_CODEC = StreamCodec.composite(
			ModernStreamCodecs.map(HashMap::new, DataComponentType.REGISTRY_CODEC, CommonStreamCodecs.INT, 256),
			HashedPatchMap::addedComponents,
			ModernStreamCodecs.collection(HashSet::new, DataComponentType.REGISTRY_CODEC, 256),
			HashedPatchMap::removedComponents,
			HashedPatchMap::new
	);

	public static HashedPatchMap create(DataComponentPatch patch, HashedPatchMap.HashGenerator hasher) {
		final DataComponentPatch.SplitResult split = patch.split();
		final Map<DataComponentType<?>, Integer> setComponentHashes = new IdentityHashMap<>(split.added().size());
		split.added().forEach(typedDataComponent -> setComponentHashes.put(typedDataComponent.type(), hasher.hash(typedDataComponent)));
		return new HashedPatchMap(setComponentHashes, split.removed());
	}

	public interface HashGenerator {
		int hash(final TypedDataComponent<?> typedDataComponent);
	}
}

