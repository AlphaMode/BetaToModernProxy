package me.alphamode.beta.proxy.data.registries;

import me.alphamode.beta.proxy.data.biome.BetaBiomeData;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;

public class BetaRegistries {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.BIOME, BetaBiomeData::bootstrap)
            .add(Registries.CHAT_TYPE, BetaRegistries::emptyBootstrap)
            .add(Registries.TRIM_PATTERN, BetaRegistries::emptyBootstrap)
            .add(Registries.TRIM_MATERIAL, BetaRegistries::emptyBootstrap)
            .add(Registries.WOLF_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.WOLF_SOUND_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.PIG_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.FROG_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.CAT_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.COW_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.CHICKEN_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.ZOMBIE_NAUTILUS_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.PAINTING_VARIANT, BetaRegistries::emptyBootstrap)
            .add(Registries.DIMENSION_TYPE, BetaRegistries::emptyBootstrap)
            .add(Registries.DAMAGE_TYPE, BetaRegistries::emptyBootstrap)
            .add(Registries.BANNER_PATTERN, BetaRegistries::emptyBootstrap)
            .add(Registries.ENCHANTMENT, BetaRegistries::emptyBootstrap)
            .add(Registries.JUKEBOX_SONG, BetaRegistries::emptyBootstrap)
            .add(Registries.INSTRUMENT, BetaRegistries::emptyBootstrap)
            .add(Registries.TEST_ENVIRONMENT, BetaRegistries::emptyBootstrap)
            .add(Registries.TEST_INSTANCE, BetaRegistries::emptyBootstrap)
            .add(Registries.DIALOG, BetaRegistries::emptyBootstrap)
            .add(Registries.TIMELINE, BetaRegistries::emptyBootstrap);

    private static <T> void emptyBootstrap(final BootstrapContext<T> ctx) {}

    public static HolderLookup.Provider createLookup() {
        RegistryAccess.Frozen staticRegistries = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
        return BUILDER.build(staticRegistries);
    }
}
