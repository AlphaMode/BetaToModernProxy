package me.alphamode.beta.proxy.util.data.modern.components;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DataComponentPatch {
	public static final DataComponentPatch EMPTY = new DataComponentPatch(Reference2ObjectMaps.emptyMap());

	public static final StreamCodec<ByteBuf, DataComponentPatch> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final DataComponentPatch patch) {
			if (patch == EMPTY) {
				ModernStreamCodecs.VAR_INT.encode(buf, 0);
				ModernStreamCodecs.VAR_INT.encode(buf, 0);
			} else {
				// TODO: actually write components
				ModernStreamCodecs.VAR_INT.encode(buf, 0);
				ModernStreamCodecs.VAR_INT.encode(buf, 0);
			}
		}

		@Override
		public DataComponentPatch decode(final ByteBuf buf) {
			final int componentsToAdd = ModernStreamCodecs.VAR_INT.decode(buf);
			final int componentsToRemove = ModernStreamCodecs.VAR_INT.decode(buf);
			if (componentsToAdd == 0 || componentsToRemove == 0) {
				return EMPTY;
			} else {
				final DataComponentPatch patch = new DataComponentPatch(Reference2ObjectMaps.emptyMap());
				for (int i = 0; i < componentsToAdd; i++) {
					final int type = ModernStreamCodecs.VAR_INT.decode(buf);
					// TODO
				}

				for (int i = 0; i < componentsToRemove; i++) {
					final int type = ModernStreamCodecs.VAR_INT.decode(buf);
					// TODO
				}

				return patch;
			}
		}
	};

	private final Reference2ObjectMap<DataComponentType<?>, Optional<?>> map;

	public DataComponentPatch(Reference2ObjectMap<DataComponentType<?>, Optional<?>> map) {
		this.map = map;
	}

	public static DataComponentPatch.Builder builder() {
		return new DataComponentPatch.Builder();
	}

	public <T> @Nullable Optional<? extends T> get(DataComponentType<? extends T> type) {
		return (Optional<? extends T>) this.map.get(type);
	}

	public Set<Map.Entry<DataComponentType<?>, Optional<?>>> entrySet() {
		return this.map.entrySet();
	}

	public int size() {
		return this.map.size();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	@Override
	public boolean equals(final Object object) {
		return this == object ? true : object instanceof DataComponentPatch patch && this.map.equals(patch.map);
	}

	@Override
	public int hashCode() {
		return this.map.hashCode();
	}

	@Override
	public String toString() {
		return toString(this.map);
	}

	private static String toString(final Reference2ObjectMap<DataComponentType<?>, Optional<?>> map) {
		final StringBuilder builder = new StringBuilder();
		builder.append('{');

		boolean first = true;
		for (final Reference2ObjectMap.Entry<DataComponentType<?>, Optional<?>> dataComponentTypeOptionalEntry : Reference2ObjectMaps.fastIterable(map)) {
			final Map.Entry<DataComponentType<?>, Optional<?>> entry = dataComponentTypeOptionalEntry;
			if (first) {
				first = false;
			} else {
				builder.append(", ");
			}

			final Optional<?> value = entry.getValue();
			value.ifPresentOrElse((val) -> {
				builder.append(entry.getKey());
				builder.append("=>");
				builder.append(val);
			}, () -> {
				builder.append("!");
				builder.append(entry.getKey());
			});
		}

		builder.append('}');
		return builder.toString();
	}

	public static class Builder {
		private final Reference2ObjectMap<DataComponentType<?>, Optional<?>> map = new Reference2ObjectArrayMap();

		private Builder() {
		}

		public <T> DataComponentPatch.Builder set(DataComponentType<T> type, T value) {
			this.map.put(type, Optional.of(value));
			return this;
		}

		public <T> DataComponentPatch.Builder remove(DataComponentType<T> type) {
			this.map.put(type, Optional.empty());
			return this;
		}

		public <T> DataComponentPatch.Builder set(TypedDataComponent<T> component) {
			return this.set(component.type(), component.value());
		}

		public DataComponentPatch build() {
			return this.map.isEmpty() ? DataComponentPatch.EMPTY : new DataComponentPatch(this.map);
		}
	}
}
