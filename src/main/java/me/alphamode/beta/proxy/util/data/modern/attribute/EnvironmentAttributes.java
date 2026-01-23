//package me.alphamode.beta.proxy.util.data.modern.attribute;
//
//import net.lenni0451.mcstructs.core.Identifier;
//
//import java.util.List;
//
//public interface EnvironmentAttributes {
//    EnvironmentAttribute<Integer> FOG_COLOR = register(
//            "visual/fog_color"
//    );
//    EnvironmentAttribute<Float> FOG_START_DISTANCE = register(
//            "visual/fog_start_distance"
//    );
//    EnvironmentAttribute<Float> FOG_END_DISTANCE = register(
//            "visual/fog_end_distance"
//    );
//    EnvironmentAttribute<Float> SKY_FOG_END_DISTANCE = register(
//            "visual/sky_fog_end_distance"
//    );
//    EnvironmentAttribute<Float> CLOUD_FOG_END_DISTANCE = register(
//            "visual/cloud_fog_end_distance"
//    );
//    EnvironmentAttribute<Integer> WATER_FOG_COLOR = register(
//            "visual/water_fog_color"
//    );
//    EnvironmentAttribute<Float> WATER_FOG_START_DISTANCE = register(
//            "visual/water_fog_start_distance"
//    );
//    EnvironmentAttribute<Float> WATER_FOG_END_DISTANCE = register(
//            "visual/water_fog_end_distance"
//    );
//    EnvironmentAttribute<Integer> SKY_COLOR = register(
//            "visual/sky_color"
//    );
//    EnvironmentAttribute<Integer> SUNRISE_SUNSET_COLOR = register(
//            "visual/sunrise_sunset_color"
//    );
//    EnvironmentAttribute<Integer> CLOUD_COLOR = register(
//            "visual/cloud_color"
//    );
//    EnvironmentAttribute<Float> CLOUD_HEIGHT = register(
//            "visual/cloud_height"
//    );
//    EnvironmentAttribute<Float> SUN_ANGLE = register(
//            "visual/sun_angle"
//    );
//    EnvironmentAttribute<Float> MOON_ANGLE = register(
//            "visual/moon_angle"
//    );
//    EnvironmentAttribute<Float> STAR_ANGLE = register(
//            "visual/star_angle"
//    );
//    EnvironmentAttribute<MoonPhase> MOON_PHASE = register(
//            "visual/moon_phase"
//    );
//    EnvironmentAttribute<Float> STAR_BRIGHTNESS = register(
//            "visual/star_brightness"
//    );
//    EnvironmentAttribute<Integer> SKY_LIGHT_COLOR = register(
//            "visual/sky_light_color"
//    );
//    EnvironmentAttribute<Float> SKY_LIGHT_FACTOR = register(
//            "visual/sky_light_factor"
//    );
//    EnvironmentAttribute<ParticleOptions> DEFAULT_DRIPSTONE_PARTICLE = register(
//            "visual/default_dripstone_particle"
//    );
//    EnvironmentAttribute<List<AmbientParticle>> AMBIENT_PARTICLES = register(
//            "visual/ambient_particles"
//    );
//    EnvironmentAttribute<BackgroundMusic> BACKGROUND_MUSIC = register(
//            "audio/background_music"
//    );
//    EnvironmentAttribute<Float> MUSIC_VOLUME = register(
//            "audio/music_volume"
//    );
//    EnvironmentAttribute<AmbientSounds> AMBIENT_SOUNDS = register(
//            "audio/ambient_sounds"
//    );
//    EnvironmentAttribute<Boolean> FIREFLY_BUSH_SOUNDS = register(
//            "audio/firefly_bush_sounds"
//    );
//    EnvironmentAttribute<Float> SKY_LIGHT_LEVEL = register(
//            "gameplay/sky_light_level"
//    );
//    EnvironmentAttribute<Boolean> WATER_EVAPORATES = register(
//            "gameplay/water_evaporates"
//    );
//    EnvironmentAttribute<Boolean> FAST_LAVA = register(
//            "gameplay/fast_lava"
//    );
//    EnvironmentAttribute<Boolean> PIGLINS_ZOMBIFY = register(
//            "gameplay/piglins_zombify"
//    );
//    EnvironmentAttribute<Boolean> CREAKING_ACTIVE = register(
//            "gameplay/creaking_active"
//    );
//
//    static <V> EnvironmentAttribute<V> register(String name) {
//        return new EnvironmentAttribute<>(Identifier.defaultNamespace(name));
//    }
//}
