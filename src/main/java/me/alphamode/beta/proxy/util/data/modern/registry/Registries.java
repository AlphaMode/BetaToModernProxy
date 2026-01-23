package me.alphamode.beta.proxy.util.data.modern.registry;

import net.lenni0451.mcstructs.core.Identifier;

public class Registries {
	public static final Identifier ROOT_REGISTRY_NAME = Identifier.defaultNamespace("root");
	public static final ResourceKey<Registry<Identifier>> DIMENSION = createRegistryKey("dimension");

	private static <T> ResourceKey<Registry<T>> createRegistryKey(final String name) {
		return ResourceKey.createRegistryKey(Identifier.defaultNamespace(name));
	}
}
