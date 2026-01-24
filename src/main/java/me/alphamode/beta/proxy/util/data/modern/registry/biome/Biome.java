package me.alphamode.beta.proxy.util.data.modern.registry.biome;

import me.alphamode.beta.proxy.util.StringRepresentable;
import me.alphamode.beta.proxy.util.data.modern.attribute.EnvironmentAttributeMap;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.codec.map.MapCodecMerger;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;

/// Only has biome data that is sent over the network
public record Biome(Biome.ClimateSettings climateSettings,
					EnvironmentAttributeMap attributes,
					BiomeSpecialEffects specialEffects) {
	public static final Codec<Biome> CODEC = MapCodecMerger.codec(
			ClimateSettings.CODEC, Biome::climateSettings,
			EnvironmentAttributeMap.CODEC.mapCodec("attributes").optional().defaulted(EnvironmentAttributeMap.EMPTY), Biome::attributes,
			BiomeSpecialEffects.CODEC.mapCodec("effects").required(), Biome::specialEffects,
			Biome::new
	);

	private record ClimateSettings(boolean hasPrecipitation, float temperature,
								   Biome.TemperatureModifier temperatureModifier, float downfall) {
		public static final MapCodec<ClimateSettings> CODEC = MapCodecMerger.mapCodec(
				Codec.BOOLEAN.mapCodec("has_precipitation").required(), ClimateSettings::hasPrecipitation,
				Codec.FLOAT.mapCodec("temperature").required(), ClimateSettings::temperature,
				Biome.TemperatureModifier.CODEC.mapCodec("temperature_modifier").optional().defaulted(Biome.TemperatureModifier.NONE), ClimateSettings::temperatureModifier,
				Codec.FLOAT.mapCodec("downfall").required(), ClimateSettings::downfall,
				Biome.ClimateSettings::new
		);
	}

	public enum TemperatureModifier implements StringRepresentable {
		NONE("none"),
		FROZEN("frozen");

		private final String name;
		public static final StringRepresentable.EnumCodec<TemperatureModifier> CODEC = StringRepresentable.fromEnum(TemperatureModifier::values);

		TemperatureModifier(final String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public String getSerializedName() {
			return this.name;
		}
	}
}
