package me.alphamode.beta.proxy.data.registries;

import me.alphamode.beta.proxy.data.biome.BetaBiomeData;
import me.alphamode.beta.proxy.data.dimension.BetaDimensionTypes;
import me.alphamode.beta.proxy.data.variants.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.animal.feline.CatVariants;
import net.minecraft.world.entity.animal.frog.FrogVariants;
import net.minecraft.world.entity.animal.nautilus.ZombieNautilus;
import net.minecraft.world.entity.animal.nautilus.ZombieNautilusVariants;
import net.minecraft.world.entity.animal.wolf.WolfSoundVariants;
import net.minecraft.world.timeline.Timelines;

public class BetaRegistries {
	public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
			.add(Registries.BIOME, BetaBiomeData::bootstrap)
			.add(Registries.CHAT_TYPE, BetaRegistries::emptyBootstrap)
			.add(Registries.TRIM_PATTERN, BetaRegistries::emptyBootstrap)
			.add(Registries.TRIM_MATERIAL, BetaRegistries::emptyBootstrap)
			.add(Registries.WOLF_VARIANT, BetaWolfVariants::bootstrap)
			.add(Registries.WOLF_SOUND_VARIANT, WolfSoundVariants::bootstrap)
			.add(Registries.PIG_VARIANT, BetaPigVariants::bootstrap)
			.add(Registries.FROG_VARIANT, FrogVariants::bootstrap)
			.add(Registries.CAT_VARIANT, CatVariants::bootstrap)
			.add(Registries.COW_VARIANT, BetaCowVariants::bootstrap)
			.add(Registries.CHICKEN_VARIANT, BetaChickenVariants::bootstrap)
			.add(Registries.ZOMBIE_NAUTILUS_VARIANT, ZombieNautilusVariants::bootstrap)
			.add(Registries.PAINTING_VARIANT, BetaPaintingVariant::bootstrap)
			.add(Registries.DIMENSION_TYPE, BetaDimensionTypes::bootstrap)
			.add(Registries.DAMAGE_TYPE, DamageTypes::bootstrap)
			.add(Registries.BANNER_PATTERN, BetaRegistries::emptyBootstrap)
			.add(Registries.ENCHANTMENT, BetaRegistries::emptyBootstrap)
			.add(Registries.JUKEBOX_SONG, BetaRegistries::emptyBootstrap)
			.add(Registries.INSTRUMENT, BetaRegistries::emptyBootstrap)
			.add(Registries.TEST_ENVIRONMENT, BetaRegistries::emptyBootstrap)
			.add(Registries.TEST_INSTANCE, BetaRegistries::emptyBootstrap)
			.add(Registries.DIALOG, BetaRegistries::emptyBootstrap)
			.add(Registries.TIMELINE, Timelines::bootstrap);

	private static <T> void emptyBootstrap(final BootstrapContext<T> ctx) {
	}

	public static HolderLookup.Provider createLookup() {
		RegistryAccess.Frozen staticRegistries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
		return BUILDER.build(staticRegistries);
	}
}
