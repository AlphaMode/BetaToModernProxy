package me.alphamode.beta.proxy.util.data.modern.registry;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

import java.util.concurrent.ConcurrentMap;

public class ResourceKey<T> {
	private static final ConcurrentMap<InternKey, ResourceKey<?>> VALUES = new MapMaker().weakValues().makeMap();
	private final Identifier registryName;
	private final Identifier identifier;

	private ResourceKey(Identifier registryName, Identifier identifier) {
		this.registryName = registryName;
		this.identifier = identifier;
	}

	public static <T> StreamCodec<ByteBuf, ResourceKey<T>> codec(ResourceKey<? extends Registry<T>> registryName) {
		return null; // TODO: Identifier.STREAM_CODEC.map(name -> create(registryName, name), ResourceKey::identifier);
	}

	public static <T> ResourceKey<T> create(ResourceKey<? extends Registry<T>> registryName, Identifier location) {
		return create(registryName.identifier, location);
	}

	public static <T> ResourceKey<Registry<T>> createRegistryKey(Identifier identifier) {
		return create(Registries.ROOT_REGISTRY_NAME, identifier);
	}

	private static <T> ResourceKey<T> create(Identifier registryName, Identifier identifier) {
		return (ResourceKey<T>) VALUES.computeIfAbsent(new ResourceKey.InternKey(registryName, identifier), k -> new ResourceKey(k.registry, k.identifier));
	}

	@Override
	public String toString() {
		return "ResourceKey[" + this.registryName + " / " + this.identifier + "]";
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

	private record InternKey(Identifier registry, Identifier identifier) {
	}
}
