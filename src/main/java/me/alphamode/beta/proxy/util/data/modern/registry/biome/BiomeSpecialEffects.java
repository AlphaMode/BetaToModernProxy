package me.alphamode.beta.proxy.util.data.modern.registry.biome;

import me.alphamode.beta.proxy.util.StringRepresentable;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;

import java.util.Optional;

public record BiomeSpecialEffects(
        int waterColor,
        Optional<Integer> foliageColorOverride,
        Optional<Integer> dryFoliageColorOverride,
        Optional<Integer> grassColorOverride,
        BiomeSpecialEffects.GrassColorModifier grassColorModifier
) {
    public static final Codec<BiomeSpecialEffects> CODEC = MapCodecMerger.codec(
            ModernCodecs.STRING_RGB_COLOR.mapCodec("water_color").required(), BiomeSpecialEffects::waterColor,
            ModernCodecs.optional("foliage_color", ModernCodecs.STRING_RGB_COLOR), BiomeSpecialEffects::foliageColorOverride,
            ModernCodecs.optional("dry_foliage_color", ModernCodecs.STRING_RGB_COLOR), BiomeSpecialEffects::dryFoliageColorOverride,
            ModernCodecs.optional("grass_color", ModernCodecs.STRING_RGB_COLOR), BiomeSpecialEffects::grassColorOverride,
            ModernCodecs.optional("grass_color_modifier", GrassColorModifier.CODEC, GrassColorModifier.NONE), BiomeSpecialEffects::grassColorModifier,
            BiomeSpecialEffects::new
    );

    public enum GrassColorModifier implements StringRepresentable {
        NONE("none"),
        DARK_FOREST("dark_forest"),
        SWAMP("swamp");

        private final String name;
        public static final Codec<GrassColorModifier> CODEC = StringRepresentable.fromEnum(BiomeSpecialEffects.GrassColorModifier::values);

        GrassColorModifier(final String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
