package me.alphamode.beta.proxy.data.variants;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public class BetaChickenVariants {
    public static void bootstrap(final BootstrapContext<ChickenVariant> context) {
        register(context, ChickenVariants.TEMPERATE, ChickenVariant.ModelType.NORMAL, "temperate_chicken", SpawnPrioritySelectors.fallback(0));
    }

    private static void register(
            final BootstrapContext<ChickenVariant> context,
            final ResourceKey<ChickenVariant> name,
            final ChickenVariant.ModelType modelType,
            final String textureName,
            final SpawnPrioritySelectors selectors
    ) {
        Identifier textureId = Identifier.withDefaultNamespace("entity/chicken/" + textureName);
        context.register(name, new ChickenVariant(new ModelAndTexture<>(modelType, textureId), selectors));
    }
}
