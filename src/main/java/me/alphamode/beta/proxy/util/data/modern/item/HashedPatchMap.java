package me.alphamode.beta.proxy.util.data.modern.item;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentType;
import me.alphamode.beta.proxy.util.data.modern.components.TypedDataComponent;

import java.util.*;
import java.util.function.Function;

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
		Map<DataComponentType<?>, Integer> setComponentHashes = new IdentityHashMap<>(split.added().size());
		split.added().forEach(typedDataComponent -> setComponentHashes.put(typedDataComponent.type(), hasher.apply(typedDataComponent)));
		return new HashedPatchMap(setComponentHashes, split.removed());
	}

	public boolean matches(DataComponentPatch patch, HashedPatchMap.HashGenerator hasher) {
		DataComponentPatch.SplitResult split = patch.split();
		if (!split.removed().equals(this.removedComponents)) {
			return false;
		} else if (this.addedComponents.size() != split.added().size()) {
			return false;
		} else {
			for (TypedDataComponent<?> typedDataComponent : split.added()) {
				Integer expectedHash = this.addedComponents.get(typedDataComponent.type());
				if (expectedHash == null) {
					return false;
				}

				Integer actualHash = hasher.apply(typedDataComponent);
				if (!actualHash.equals(expectedHash)) {
					return false;
				}
			}

			return true;
		}
	}

	@FunctionalInterface
	public interface HashGenerator extends Function<TypedDataComponent<?>, Integer> {
	}
}

