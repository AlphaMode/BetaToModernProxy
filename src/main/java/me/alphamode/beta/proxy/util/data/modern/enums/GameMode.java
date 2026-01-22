package me.alphamode.beta.proxy.util.data.modern.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

import java.util.function.IntFunction;

public enum GameMode {
	SURVIVAL(0),
	CREATIVE(1),
	ADVENTURE(2),
	SPECTATOR(3);

	private static final IntFunction<GameMode> BY_ID = ByIdMap.continuous(GameMode::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteBuf, GameMode> CODEC = ModernCodecs.idMapper(BY_ID, GameMode::getId);

	private final int id;

	GameMode(final int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
