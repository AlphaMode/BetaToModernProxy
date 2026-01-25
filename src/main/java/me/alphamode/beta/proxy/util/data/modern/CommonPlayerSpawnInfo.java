package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import me.alphamode.beta.proxy.util.data.modern.registry.Registries;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record CommonPlayerSpawnInfo(
		Holder<DimensionType> dimensionType,
		ResourceKey<Identifier> dimension,
		long seed,
		GameMode gameMode,
		@Nullable GameMode previousGameType,
		boolean isDebug,
		boolean isFlat,
		Optional<GlobalPos> lastDeathLocation,
		int portalCooldown,
		int seaLevel
) {
	public static final StreamCodec<ByteBuf, CommonPlayerSpawnInfo> CODEC = StreamCodec.composite(
			DimensionType.CODEC,
			CommonPlayerSpawnInfo::dimensionType,
			ResourceKey.streamCodec(Registries.DIMENSION),
			CommonPlayerSpawnInfo::dimension,
			BasicStreamCodecs.LONG,
			CommonPlayerSpawnInfo::seed,
			GameMode.BYTE_CODEC,
			CommonPlayerSpawnInfo::gameMode,
			GameMode.NULLABLE_BYTE_CODEC,
			CommonPlayerSpawnInfo::previousGameType,
			BasicStreamCodecs.BOOL,
			CommonPlayerSpawnInfo::isDebug,
			BasicStreamCodecs.BOOL,
			CommonPlayerSpawnInfo::isFlat,
			ModernStreamCodecs.optional(GlobalPos.CODEC),
			CommonPlayerSpawnInfo::lastDeathLocation,
			ModernStreamCodecs.VAR_INT,
			CommonPlayerSpawnInfo::portalCooldown,
			ModernStreamCodecs.VAR_INT,
			CommonPlayerSpawnInfo::seaLevel,
			CommonPlayerSpawnInfo::new
	);
}
