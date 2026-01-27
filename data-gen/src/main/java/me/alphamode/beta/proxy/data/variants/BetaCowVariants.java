package me.alphamode.beta.proxy.data.variants;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.cow.CowVariant;
import net.minecraft.world.entity.animal.cow.CowVariants;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public class BetaCowVariants {
    public static void bootstrap(final BootstrapContext<CowVariant> context) {
        register(context, CowVariants.TEMPERATE, CowVariant.ModelType.NORMAL, "temperate_cow", SpawnPrioritySelectors.fallback(0));
    }

    private static void register(
            final BootstrapContext<CowVariant> context,
            final ResourceKey<CowVariant> name,
            final CowVariant.ModelType modelType,
            final String textureName,
            final SpawnPrioritySelectors selectors
    ) {
        Identifier textureId = Identifier.withDefaultNamespace("entity/cow/" + textureName);
        context.register(name, new CowVariant(new ModelAndTexture<>(modelType, textureId), selectors));
    }
}
