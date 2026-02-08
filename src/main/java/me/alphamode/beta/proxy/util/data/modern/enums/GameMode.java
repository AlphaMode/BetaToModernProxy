package me.alphamode.beta.proxy.util.data.modern.enums;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.IntFunction;

public enum GameMode {
	SURVIVAL(0),
	CREATIVE(1),
	ADVENTURE(2),
	SPECTATOR(3);

	private static final IntFunction<GameMode> BY_ID = ByIdMap.continuous(GameMode::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteStream, GameMode> CODEC = ModernStreamCodecs.idMapper(BY_ID, GameMode::getId);

	// TODO: make proper
	public static final StreamCodec<ByteStream, GameMode> BYTE_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final GameMode value) {
			CommonStreamCodecs.BYTE.encode(stream, (byte) value.id);
		}

		@Override
		public GameMode decode(final ByteStream stream) {
			return GameMode.byId(CommonStreamCodecs.BYTE.decode(stream));
		}
	};

	// TODO: make proper
	public static final StreamCodec<ByteStream, GameMode> NULLABLE_BYTE_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final GameMode value) {
			CommonStreamCodecs.BYTE.encode(stream, (byte) value.id);
		}

		@Override
		public GameMode decode(final ByteStream stream) {
			return GameMode.byNullableId(CommonStreamCodecs.BYTE.decode(stream));
		}
	};

	private final int id;

	GameMode(final int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static GameMode byId(final int id) {
		return BY_ID.apply(id);
	}

	public static int getNullableId(@Nullable final GameMode gameMode) {
		return gameMode != null ? gameMode.id : -1;
	}

	public static @Nullable GameMode byNullableId(final int id) {
		return id == -1 ? null : byId(id);
	}

	public static boolean isValidId(final int id) {
		return Arrays.stream(values()).anyMatch(gameMode -> gameMode.id == id);
	}
}
