package me.alphamode.beta.proxy.data.registries;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;

@SuppressWarnings("NullableProblems")
public interface BetaBiomes {
	ResourceKey<Biome> RAINFOREST = register("rainforest");
	ResourceKey<Biome> SWAPLAND = register("swampland");
	ResourceKey<Biome> SEASONAL_FOREST = register("seasonal_forest");
	ResourceKey<Biome> FOREST = register("forest");
	ResourceKey<Biome> SAVANNA = register("savanna");
	ResourceKey<Biome> SHRUBLAND = register("shrubland");
	ResourceKey<Biome> TAIGA = register("taiga");
	ResourceKey<Biome> DESERT = register("desert");
	ResourceKey<Biome> PLAINS = register("plains");
	ResourceKey<Biome> ICE_DESERT = register("ice_desert");
	ResourceKey<Biome> TUNDRA = register("tundra");
	ResourceKey<Biome> HELL = register("hell");
	ResourceKey<Biome> SKY = register("sky");

	static ResourceKey<Biome> register(final String name) {
		return ResourceKey.create(Registries.BIOME, Identifier.fromNamespaceAndPath("minecraftbeta", name));
	}
}
