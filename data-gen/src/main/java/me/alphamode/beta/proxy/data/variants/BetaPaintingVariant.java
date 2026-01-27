package me.alphamode.beta.proxy.data.variants;

import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.decoration.painting.PaintingVariant;

import java.util.Optional;

public interface BetaPaintingVariant {
    ResourceKey<PaintingVariant> KEBAB = create("kebab");
    ResourceKey<PaintingVariant> AZTEC = create("aztec");
    ResourceKey<PaintingVariant> ALBAN = create("alban");
    ResourceKey<PaintingVariant> AZTEC2 = create("aztec2");
    ResourceKey<PaintingVariant> BOMB = create("bomb");
    ResourceKey<PaintingVariant> PLANT = create("plant");
    ResourceKey<PaintingVariant> WASTELAND = create("wasteland");
    ResourceKey<PaintingVariant> POOL = create("pool");
    ResourceKey<PaintingVariant> COURBET = create("courbet");
    ResourceKey<PaintingVariant> SEA = create("sea");
    ResourceKey<PaintingVariant> SUNSET = create("sunset");
    ResourceKey<PaintingVariant> CREEBET = create("creebet");
    ResourceKey<PaintingVariant> WANDERER = create("wanderer");
    ResourceKey<PaintingVariant> GRAHAM = create("graham");
    ResourceKey<PaintingVariant> MATCH = create("match");
    ResourceKey<PaintingVariant> BUST = create("bust");
    ResourceKey<PaintingVariant> STAGE = create("stage");
    ResourceKey<PaintingVariant> VOID = create("void");
    ResourceKey<PaintingVariant> SKULL_AND_ROSES = create("skull_and_roses");
    ResourceKey<PaintingVariant> FIGHTERS = create("fighters");
    ResourceKey<PaintingVariant> POINTER = create("pointer");
    ResourceKey<PaintingVariant> PIGSCENE = create("pigscene");
    ResourceKey<PaintingVariant> BURNING_SKULL = create("burning_skull");
    ResourceKey<PaintingVariant> SKELETON = create("skeleton");
    ResourceKey<PaintingVariant> DONKEY_KONG = create("donkey_kong");

    static void bootstrap(final BootstrapContext<PaintingVariant> context) {
        register(context, KEBAB, 1, 1);
        register(context, AZTEC, 1, 1);
        register(context, ALBAN, 1, 1);
        register(context, AZTEC2,1, 1);
        register(context, BOMB, 1, 1);
        register(context, PLANT, 1, 1);
        register(context, WASTELAND, 1, 1);
        register(context, POOL, 2, 1);
        register(context, COURBET, 2, 1);
        register(context, SEA, 2, 1);
        register(context, SUNSET, 2, 1);
        register(context, CREEBET, 2, 1);
        register(context, WANDERER, 1, 2);
        register(context, GRAHAM, 1, 2);
        register(context, MATCH, 2, 2);
        register(context, BUST, 2, 2);
        register(context, STAGE, 2, 2);
        register(context, VOID, 2, 2);
        register(context, SKULL_AND_ROSES, 2, 2);
        register(context, FIGHTERS, 4, 2);
        register(context, POINTER, 4, 4);
        register(context, PIGSCENE, 4, 4);
        register(context, BURNING_SKULL, 4, 4);
        register(context, SKELETON, 4, 3);
        register(context, DONKEY_KONG, 4, 3);
    }

    private static ResourceKey<PaintingVariant> create(final String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT, Identifier.fromNamespaceAndPath("minecraftbeta", name));
    }

    private static void register(final BootstrapContext<PaintingVariant> context, final ResourceKey<PaintingVariant> id, final int width, final int height) {
        register(context, id, width, height, true);
    }

    private static void register(
            final BootstrapContext<PaintingVariant> context, final ResourceKey<PaintingVariant> id, final int width, final int height, final boolean hasAuthor
    ) {
        context.register(
                id,
                new PaintingVariant(
                        width,
                        height,
                        id.identifier(),
                        Optional.of(Component.translatable(id.identifier().toLanguageKey("painting", "title")).withStyle(ChatFormatting.YELLOW)),
                        hasAuthor ? Optional.of(Component.translatable(id.identifier().toLanguageKey("painting", "author")).withStyle(ChatFormatting.GRAY)) : Optional.empty()
                )
        );
    }
}
