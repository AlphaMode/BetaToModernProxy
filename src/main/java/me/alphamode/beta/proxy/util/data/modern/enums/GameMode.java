package me.alphamode.beta.proxy.util.data.modern.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
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
	public static final StreamCodec<ByteBuf, GameMode> CODEC = ModernStreamCodecs.idMapper(BY_ID, GameMode::getId);

	// TODO: make proper
	public static final StreamCodec<ByteBuf, GameMode> BYTE_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final GameMode value) {
			BasicStreamCodecs.BYTE.encode(buf, (byte) value.id);
		}

		@Override
		public GameMode decode(final ByteBuf buf) {
			return GameMode.byId(BasicStreamCodecs.BYTE.decode(buf));
		}
	};

	// TODO: make proper
	public static final StreamCodec<ByteBuf, GameMode> NULLABLE_BYTE_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final GameMode value) {
			BasicStreamCodecs.BYTE.encode(buf, (byte) value.id);
		}

		@Override
		public GameMode decode(final ByteBuf buf) {
			return GameMode.byNullableId(BasicStreamCodecs.BYTE.decode(buf));
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
