package me.alphamode.beta.proxy.util.data.modern.enums;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

import java.util.function.IntFunction;

public enum HumanoidArm {
	LEFT(0),
	RIGHT(1);

	private static final IntFunction<HumanoidArm> BY_ID = ByIdMap.continuous(HumanoidArm::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
	public static final StreamCodec<ByteStream, HumanoidArm> CODEC = ModernStreamCodecs.idMapper(BY_ID, HumanoidArm::getId);
	private final int id;

	HumanoidArm(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
