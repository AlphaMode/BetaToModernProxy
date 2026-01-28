package me.alphamode.beta.proxy.util.data.modern.components;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Reference2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMaps;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.impl.v1_20_3.JavaConverter_v1_20_3;
import net.lenni0451.mcstructs.converter.impl.v1_21_5.NbtConverter_v1_21_5;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DataComponentPatch {
	private static final Logger LOGGER = LogManager.getLogger(DataComponentPatch.class);
	public static final DataComponentPatch EMPTY = new DataComponentPatch(Reference2ObjectMaps.emptyMap());

	public static final StreamCodec<CompoundTag, DataComponentPatch> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final CompoundTag tag, final DataComponentPatch value) {
			// TODO
		}

		@Override
		public DataComponentPatch decode(final CompoundTag tag) {
			final DataComponentPatch.Builder builder = DataComponentPatch.builder();
			for (final var entry : tag) {
				final PatchKey key = PatchKey.CODEC.deserialize(JavaConverter_v1_20_3.INSTANCE, entry.getKey()).get();
				if (key.removed) {
					builder.remove(key.type);
				} else {
					final Object value = key.valueCodec().deserialize(NbtConverter_v1_21_5.INSTANCE, entry.getValue());
					builder.set((DataComponentType<Object>) key.type, value);
				}
			}

			return builder.build();
		}
	};

	public static final StreamCodec<ByteBuf, DataComponentPatch> STREAM_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final DataComponentPatch patch) {
			if (patch == EMPTY) {
				ModernStreamCodecs.VAR_INT.encode(buf, 0);
				ModernStreamCodecs.VAR_INT.encode(buf, 0);
			} else {
				final Map<DataComponentType<?>, Object> toAdd = new HashMap<>();
				final List<Integer> toRemove = new ArrayList<>();
				for (final var entry : patch.entrySet()) {
					if (entry.getValue().isEmpty()) {
						toRemove.add(DataComponents.getRawId(entry.getKey()));
					} else {
						toAdd.put(entry.getKey(), entry.getValue());
					}
				}

				ModernStreamCodecs.VAR_INT.encode(buf, 0);
				ModernStreamCodecs.VAR_INT.encode(buf, toRemove.size());

				// To Add
				toAdd.forEach((type, value) -> {
					LOGGER.info("Writing to-add id of {} ({})", DataComponents.getRawId(type), type);
					/*ModernStreamCodecs.VAR_INT.encode(buf, DataComponents.getRawId(type));
					// TODO AHHHHHH
					if (value instanceof Optional<?> optionalResult && optionalResult.get() instanceof Result<?> result) {
						((StreamCodec<ByteBuf, Object>) type.streamCodec()).encode(buf, result.get());
					}*/
				});

				// To Remove
				toRemove.forEach(value -> ModernStreamCodecs.VAR_INT.encode(buf, value));
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
		return this == object || object instanceof DataComponentPatch patch && this.map.equals(patch.map);
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

		public void apply(final DataComponentPatch patch) {
			for (final var entry : patch.entrySet()) {
				this.map.put(entry.getKey(), entry.getValue());
			}
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

	private record PatchKey(DataComponentType<?> type, boolean removed) {
		public static final Codec<PatchKey> CODEC = Codec.STRING.map(output -> {
			return ""; // TODO
		}, input -> {
			final boolean removed = input.startsWith("!");
			if (removed) {
				input = input.substring("!".length());
			}

			final DataComponentType<?> type = DataComponents.byId(Identifier.tryOf(input));
			return new PatchKey(type, removed);
		});

		public Codec<?> valueCodec() {
			return this.removed ? Codec.UNIT : this.type.codecOrThrow();
		}
	}
}
