package me.alphamode.beta.proxy.util.data.modern.attribute;

import net.lenni0451.mcstructs.core.Identifier;

public interface EnvironmentAttributes {
	EnvironmentAttribute<Integer> FOG_COLOR = register(
			"visual/fog_color", AttributeTypes.RGB_COLOR
	);
	EnvironmentAttribute<Float> FOG_START_DISTANCE = register(
			"visual/fog_start_distance", AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Float> FOG_END_DISTANCE = register(
			"visual/fog_end_distance",
			AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Float> SKY_FOG_END_DISTANCE = register(
			"visual/sky_fog_end_distance",
			AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Float> CLOUD_FOG_END_DISTANCE = register(
			"visual/cloud_fog_end_distance",
			AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Integer> WATER_FOG_COLOR = register(
			"visual/water_fog_color", AttributeTypes.RGB_COLOR
	);
	EnvironmentAttribute<Float> WATER_FOG_START_DISTANCE = register(
			"visual/water_fog_start_distance", AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Float> WATER_FOG_END_DISTANCE = register(
			"visual/water_fog_end_distance",
			AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Integer> SKY_COLOR = register(
			"visual/sky_color", AttributeTypes.RGB_COLOR
	);
	EnvironmentAttribute<Integer> SUNRISE_SUNSET_COLOR = register(
			"visual/sunrise_sunset_color", AttributeTypes.ARGB_COLOR
	);
	EnvironmentAttribute<Integer> CLOUD_COLOR = register(
			"visual/cloud_color", AttributeTypes.ARGB_COLOR
	);
	EnvironmentAttribute<Float> CLOUD_HEIGHT = register(
			"visual/cloud_height", AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Float> SUN_ANGLE = register(
			"visual/sun_angle", AttributeTypes.ANGLE_DEGREES
	);
	EnvironmentAttribute<Float> MOON_ANGLE = register(
			"visual/moon_angle", AttributeTypes.ANGLE_DEGREES
	);
	EnvironmentAttribute<Float> STAR_ANGLE = register(
			"visual/star_angle", AttributeTypes.ANGLE_DEGREES
	);
	EnvironmentAttribute<MoonPhase> MOON_PHASE = register(
			"visual/moon_phase", AttributeTypes.MOON_PHASE
	);
	EnvironmentAttribute<Float> STAR_BRIGHTNESS = register(
			"visual/star_brightness",
			AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Integer> SKY_LIGHT_COLOR = register(
			"visual/sky_light_color", AttributeTypes.RGB_COLOR
	);
	EnvironmentAttribute<Float> SKY_LIGHT_FACTOR = register(
			"visual/sky_light_factor",
			AttributeTypes.FLOAT
	);
	//    EnvironmentAttribute<ParticleOptions> DEFAULT_DRIPSTONE_PARTICLE = register(
//            "visual/default_dripstone_particle", AttributeTypes.PARTICLE
//    );
//    EnvironmentAttribute<List<AmbientParticle>> AMBIENT_PARTICLES = register(
//            "visual/ambient_particles", AttributeTypes.AMBIENT_PARTICLES
//    );
//    EnvironmentAttribute<BackgroundMusic> BACKGROUND_MUSIC = register(
//            "audio/background_music", AttributeTypes.BACKGROUND_MUSIC
//    );
	EnvironmentAttribute<Float> MUSIC_VOLUME = register(
			"audio/music_volume", AttributeTypes.FLOAT
	);
	//    EnvironmentAttribute<AmbientSounds> AMBIENT_SOUNDS = register(
//            "audio/ambient_sounds", AttributeTypes.AMBIENT_SOUNDS
//    );
	EnvironmentAttribute<Boolean> FIREFLY_BUSH_SOUNDS = register(
			"audio/firefly_bush_sounds", AttributeTypes.BOOLEAN
	);
	EnvironmentAttribute<Float> SKY_LIGHT_LEVEL = register(
			"gameplay/sky_light_level",
			AttributeTypes.FLOAT
	);
	EnvironmentAttribute<Boolean> WATER_EVAPORATES = register(
			"gameplay/water_evaporates", AttributeTypes.BOOLEAN
	);
	EnvironmentAttribute<Boolean> FAST_LAVA = register(
			"gameplay/fast_lava", AttributeTypes.BOOLEAN
	);
	EnvironmentAttribute<Boolean> PIGLINS_ZOMBIFY = register(
			"gameplay/piglins_zombify", AttributeTypes.BOOLEAN
	);
	EnvironmentAttribute<Boolean> CREAKING_ACTIVE = register(
			"gameplay/creaking_active", AttributeTypes.BOOLEAN
	);

	static <V> EnvironmentAttribute<V> register(String name, final AttributeType<V> type) {
		return new EnvironmentAttribute<>(Identifier.defaultNamespace(name), type);
	}
}
