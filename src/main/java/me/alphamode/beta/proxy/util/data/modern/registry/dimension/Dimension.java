package me.alphamode.beta.proxy.util.data.modern.registry.dimension;

import me.alphamode.beta.proxy.util.data.modern.registry.Registries;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;

public class Dimension {
	public static final ResourceKey<Identifier> OVERWORLD = ResourceKey.create(Registries.DIMENSION, Identifier.defaultNamespace("overworld"));
	public static final ResourceKey<Identifier> NETHER = ResourceKey.create(Registries.DIMENSION, Identifier.defaultNamespace("nether"));
	public static final ResourceKey<Identifier> SKY = ResourceKey.create(Registries.DIMENSION, Identifier.defaultNamespace("sky"));

	public static ResourceKey<Identifier> byLegacyId(final int id) {
		if (id == 0) {
			return OVERWORLD;
		} else if (id == 1) {
			return NETHER;
		} else if (id == -1) {
			return SKY;
		} else {
			throw new RuntimeException("Invalid dimension id");
		}
	}
}
