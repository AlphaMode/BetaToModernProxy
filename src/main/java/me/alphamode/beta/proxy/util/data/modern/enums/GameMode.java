package me.alphamode.beta.proxy.util.data.modern.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

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
			BasicStreamCodecs.UNSIGNED_BYTE.encode(buf, (short) value.id);
		}

		@Override
		public GameMode decode(final ByteBuf buf) {
			return GameMode.byId(BasicStreamCodecs.UNSIGNED_BYTE.decode(buf));
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
}
