package me.alphamode.beta.proxy.data.variants;

import net.minecraft.core.ClientAsset;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.wolf.WolfVariant;
import net.minecraft.world.entity.animal.wolf.WolfVariants;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public class BetaWolfVariants {
    public static void bootstrap(final BootstrapContext<WolfVariant> context) {
        register(context, WolfVariants.PALE, "wolf", SpawnPrioritySelectors.fallback(0));
    }

    private static void register(
            final BootstrapContext<WolfVariant> context, final ResourceKey<WolfVariant> name, final String fileName, final SpawnPrioritySelectors selectors
    ) {
        Identifier wildTexture = Identifier.withDefaultNamespace("entity/wolf/" + fileName);
        Identifier tameTexture = Identifier.withDefaultNamespace("entity/wolf/" + fileName + "_tame");
        Identifier angryTexture = Identifier.withDefaultNamespace("entity/wolf/" + fileName + "_angry");
        context.register(
                name,
                new WolfVariant(
                        new WolfVariant.AssetInfo(
                                new ClientAsset.ResourceTexture(wildTexture), new ClientAsset.ResourceTexture(tameTexture), new ClientAsset.ResourceTexture(angryTexture)
                        ),
                        selectors
                )
        );
    }
}
