package me.alphamode.beta.proxy.util.data.modern.enums;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

import java.util.function.IntFunction;

public enum ParticleStatus {
	ALL(0),
	DECREASED(1),
	MINIMAL(2);

	private static final IntFunction<ParticleStatus> BY_ID = ByIdMap.continuous(ParticleStatus::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
	public static final StreamCodec<ByteStream, ParticleStatus> CODEC = ModernStreamCodecs.idMapper(BY_ID, ParticleStatus::getId);
	private final int id;

	ParticleStatus(final int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
