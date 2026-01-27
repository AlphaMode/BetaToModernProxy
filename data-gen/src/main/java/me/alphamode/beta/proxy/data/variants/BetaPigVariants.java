package me.alphamode.beta.proxy.data.variants;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.pig.PigVariant;
import net.minecraft.world.entity.animal.pig.PigVariants;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public class BetaPigVariants {
    public static void bootstrap(final BootstrapContext<PigVariant> context) {
        register(context, PigVariants.TEMPERATE, PigVariant.ModelType.NORMAL, "temperate_pig", SpawnPrioritySelectors.fallback(0));
    }

    private static void register(
            final BootstrapContext<PigVariant> context,
            final ResourceKey<PigVariant> name,
            final PigVariant.ModelType modelType,
            final String textureName,
            final SpawnPrioritySelectors selectors
    ) {
        Identifier textureId = Identifier.withDefaultNamespace("entity/pig/" + textureName);
        context.register(name, new PigVariant(new ModelAndTexture<>(modelType, textureId), selectors));
    }
}
