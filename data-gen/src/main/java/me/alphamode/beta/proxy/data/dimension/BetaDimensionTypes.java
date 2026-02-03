package me.alphamode.beta.proxy.data.dimension;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TimelineTags;
import net.minecraft.util.ARGB;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.timeline.Timeline;

public class BetaDimensionTypes {
	public static final ResourceKey<DimensionType> SKY = register("sky");

	private static ResourceKey<DimensionType> register(final String id) {
		return ResourceKey.create(Registries.DIMENSION_TYPE, Identifier.fromNamespaceAndPath("minecraftbeta", id));
	}

	public static void bootstrap(final BootstrapContext<DimensionType> ctx) {
        HolderGetter<Timeline> timelines = ctx.lookup(Registries.TIMELINE);
		EnvironmentAttributeMap overworldAttributes = EnvironmentAttributeMap.builder()
				.set(EnvironmentAttributes.FOG_COLOR, -4138753)
				.set(EnvironmentAttributes.SKY_COLOR, OverworldBiomes.calculateSkyColor(0.8F))
				.set(EnvironmentAttributes.CLOUD_COLOR, ARGB.white(0.8F))
				.set(EnvironmentAttributes.CLOUD_HEIGHT, 108.0F)
				.set(EnvironmentAttributes.BACKGROUND_MUSIC, BackgroundMusic.OVERWORLD)
				.set(EnvironmentAttributes.AMBIENT_SOUNDS, AmbientSounds.LEGACY_CAVE_SETTINGS)
				.build();

		ctx.register(BuiltinDimensionTypes.OVERWORLD, new DimensionType(
				false,
				true,
				false,
				1.0,
				0,
				128,
				128,
				BlockTags.INFINIBURN_OVERWORLD,
				0.0F,
				new DimensionType.MonsterSettings(UniformInt.of(0, 8), 0),
				DimensionType.Skybox.OVERWORLD,
				DimensionType.CardinalLightType.DEFAULT,
				overworldAttributes,
                timelines.getOrThrow(TimelineTags.IN_OVERWORLD)
		));

		ctx.register(BuiltinDimensionTypes.NETHER, new DimensionType(
				true,
				false,
				true,
				8.0,
				0,
				128,
				128,
				BlockTags.INFINIBURN_NETHER,
				0.1F,
				new DimensionType.MonsterSettings(ConstantInt.of(7), 15),
				DimensionType.Skybox.NONE,
				DimensionType.CardinalLightType.NETHER,
				EnvironmentAttributeMap.EMPTY,
				HolderSet.empty()
		));

		ctx.register(SKY, new DimensionType(
				true,
				true,
				false,
				1.0,
				0,
				128,
				128,
				BlockTags.INFINIBURN_OVERWORLD,
				0.0F,
				new DimensionType.MonsterSettings(UniformInt.of(0, 8), 0),
				DimensionType.Skybox.OVERWORLD,
				DimensionType.CardinalLightType.DEFAULT,
				EnvironmentAttributeMap.builder()
						.set(EnvironmentAttributes.CLOUD_HEIGHT, 8.0F)
						.build(),
				HolderSet.empty()
		));
	}
}
