package me.alphamode.beta.proxy.util.data.modern.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

import java.util.function.IntFunction;

public enum Difficulty {
	PEACEFUL(0),
	EASY(1),
	NORMAL(2),
	HARD(3);

	private static final IntFunction<Difficulty> BY_ID = ByIdMap.continuous(Difficulty::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
	public static final StreamCodec<ByteBuf, Difficulty> CODEC = ModernStreamCodecs.idMapper(BY_ID, Difficulty::getId);
	private final int id;

	Difficulty(final int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
