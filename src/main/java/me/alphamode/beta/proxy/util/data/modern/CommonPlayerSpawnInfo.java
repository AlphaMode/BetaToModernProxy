package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import me.alphamode.beta.proxy.util.data.modern.registry.Registry;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record CommonPlayerSpawnInfo(
		Holder<DimensionType> dimensionType,
		ResourceKey<Registry<Identifier>> dimension,
		long seed,
		GameMode gameType,
		@Nullable GameMode previousGameType,
		boolean isDebug,
		boolean isFlat,
		Optional<GlobalPos> lastDeathLocation,
		int portalCooldown,
		int seaLevel
) {
//	public static final StreamCodec<ByteBuf, CommonPlayerSpawnInfo> CODEC = StreamCodec.composite(
//			DimensionType.CODEC,
//			CommonPlayerSpawnInfo::dimensionType,
//			ResourceKey.streamCodec(Registries.DIMENSION),
//			CommonPlayerSpawnInfo::dimension,
//			BasicStreamCodecs.LONG,
//			CommonPlayerSpawnInfo::seed,
//			GameType.STREAM_CODEC_BYTE,
//			CommonPlayerSpawnInfo::gameType,
//			ModernStreamCodecs.nullable(GameType.STREAM_CODEC_BYTE),
//			CommonPlayerSpawnInfo::gameType,
//			BasicStreamCodecs.BOOL,
//			CommonPlayerSpawnInfo::isDebug,
//			BasicStreamCodecs.BOOL,
//			CommonPlayerSpawnInfo::isFlat,
//			ModernStreamCodecs.optional(GlobalPos.CODEC),
//			CommonPlayerSpawnInfo::lastDeathLocation,
//			BasicStreamCodecs.INT,
//			CommonPlayerSpawnInfo::portalCooldown,
//			BasicStreamCodecs.INT,
//			CommonPlayerSpawnInfo::seaLevel,
//			CommonPlayerSpawnInfo::new
//	);
}
