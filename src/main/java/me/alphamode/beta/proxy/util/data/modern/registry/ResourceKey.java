package me.alphamode.beta.proxy.util.data.modern.registry;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ResourceKey<T> {
	public static final StreamCodec<ByteStream, ResourceKey<? extends Registry<?>>> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final ResourceKey<? extends Registry<?>> value) {
			ModernStreamCodecs.IDENTIFIER.encode(buf, value.identifier());
		}

		@Override
		public ResourceKey<? extends Registry<?>> decode(final ByteStream buf) {
			return ResourceKey.createRegistryKey(ModernStreamCodecs.IDENTIFIER.decode(buf));
		}
	};

	private static final ConcurrentMap<InternKey, ResourceKey<?>> VALUES = new ConcurrentHashMap<>();
	private final Identifier registryName;
	private final Identifier identifier;

	private ResourceKey(final Identifier registryName, final Identifier identifier) {
		this.registryName = registryName;
		this.identifier = identifier;
	}

	public static <T> StreamCodec<ByteStream, ResourceKey<T>> streamCodec(final ResourceKey<? extends Registry<T>> registryName) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final ResourceKey<T> value) {
				ModernStreamCodecs.IDENTIFIER.encode(buf, value.identifier);
			}

			@Override
			public ResourceKey<T> decode(final ByteStream buf) {
				return ResourceKey.create(registryName, ModernStreamCodecs.IDENTIFIER.decode(buf));
			}
		};
	}

	public static <T> ResourceKey<T> create(final ResourceKey<? extends Registry<T>> registryName, final Identifier location) {
		return create(registryName.identifier, location);
	}

	public static <T> ResourceKey<Registry<T>> createRegistryKey(final Identifier identifier) {
		return create(Registries.ROOT_REGISTRY_NAME, identifier);
	}

	private static <T> ResourceKey<T> create(final Identifier registryName, final Identifier identifier) {
		return (ResourceKey<T>) VALUES.computeIfAbsent(new ResourceKey.InternKey(registryName, identifier), k -> new ResourceKey(k.registry, k.identifier));
	}

	public Identifier identifier() {
		return this.identifier;
	}

	public Identifier registry() {
		return this.registryName;
	}

	public ResourceKey<Registry<T>> registryKey() {
		return createRegistryKey(this.registryName);
	}

	@Override
	public String toString() {
		return "ResourceKey[" + this.registryName + " / " + this.identifier + "]";
	}

	private record InternKey(Identifier registry, Identifier identifier) {
	}
}
