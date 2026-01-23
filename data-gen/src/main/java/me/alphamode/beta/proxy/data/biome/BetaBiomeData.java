package me.alphamode.beta.proxy.data.biome;

import me.alphamode.beta.proxy.data.registries.BetaBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class BetaBiomeData {
    public static void bootstrap(final BootstrapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(BetaBiomes.RAINFOREST, betaBiome(placedFeatures, carvers, builder().setColor(588342).setName("Rainforest").setLeafColor(2094168)));
        context.register(BetaBiomes.SWAPLAND, betaBiome(placedFeatures, carvers, builder().setColor(522674).setName("Swampland").setLeafColor(9154376)));
        context.register(BetaBiomes.SEASONAL_FOREST, betaBiome(placedFeatures, carvers, builder().setColor(10215459).setName("Seasonal Forest")));
        context.register(BetaBiomes.FOREST, betaBiome(placedFeatures, carvers, builder().setColor(353825).setName("Forest").setLeafColor(5159473)));
        context.register(BetaBiomes.SAVANNA, betaBiome(placedFeatures, carvers, builder().setColor(14278691).setName("Savanna")));
        context.register(BetaBiomes.SHRUBLAND, betaBiome(placedFeatures, carvers, builder().setColor(10595616).setName("Shrubland")));
        context.register(BetaBiomes.TAIGA, betaBiome(placedFeatures, carvers, builder().setColor(3060051).setName("Taiga").setSnowCovered().setLeafColor(8107825)));
        context.register(BetaBiomes.DESERT, betaBiome(placedFeatures, carvers, builder().setColor(16421912).setName("Desert").setNoRain()));
        context.register(BetaBiomes.PLAINS, betaBiome(placedFeatures, carvers, builder().setColor(16767248).setName("Plains")));
        context.register(BetaBiomes.ICE_DESERT, betaBiome(placedFeatures, carvers, builder().setColor(16772499).setName("Ice Desert").setSnowCovered().setNoRain().setLeafColor(12899129)));
        context.register(BetaBiomes.TUNDRA, betaBiome(placedFeatures, carvers, builder().setColor(5762041).setName("Tundra").setSnowCovered().setLeafColor(12899129)));
        context.register(BetaBiomes.HELL, betaBiome(placedFeatures, carvers, builder().setColor(16711680).setName("Hell").setNoRain()));
        context.register(BetaBiomes.SKY, betaBiome(placedFeatures, carvers, builder().setColor(8421631).setName("Sky").setNoRain()));
    }

    public static BetaBiomeBuilder builder() {
        return new BetaBiomeBuilder();
    }

    public static class BetaBiomeBuilder {
        private String name;
        private int color;
        private int leafColor = 5169201;
        private boolean hasPrecipitation = true;
        private boolean snowCovered;

        public BetaBiomeBuilder setColor(int color) {
            this.color = color;
            return this;
        }

        public BetaBiomeBuilder setLeafColor(int color) {
            this.leafColor = color;
            return this;
        }

        public BetaBiomeBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public BetaBiomeBuilder setNoRain() {
            this.hasPrecipitation = false;
            return this;
        }

        public BetaBiomeBuilder setSnowCovered() {
            this.snowCovered = true;
            return this;
        }
    }

    public static Biome betaBiome(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> carvers, BetaBiomeBuilder builder) {
        final BiomeSpecialEffects.Builder specialEffects = new BiomeSpecialEffects.Builder();
        specialEffects.waterColor(16777215);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(builder.hasPrecipitation)
                .temperature(builder.snowCovered ? -0.5F : 2.0F)
                .downfall(1.0F)
                .specialEffects(specialEffects.build())
                .mobSpawnSettings(new MobSpawnSettings.Builder().build())
                .generationSettings(new BiomeGenerationSettings.Builder(placedFeatures, carvers).build())
                .build();
    }
}
