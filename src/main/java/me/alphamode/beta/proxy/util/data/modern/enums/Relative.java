package me.alphamode.beta.proxy.util.data.modern.enums;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.EnumSet;
import java.util.Set;

public enum Relative {
	X(0),
	Y(1),
	Z(2),
	Y_ROT(3),
	X_ROT(4),
	DELTA_X(5),
	DELTA_Y(6),
	DELTA_Z(7),
	ROTATE_DELTA(8);

	public static final Set<Relative> ALL = Set.of(values());
	public static final Set<Relative> ROTATION = Set.of(X_ROT, Y_ROT);
	public static final Set<Relative> DELTA = Set.of(DELTA_X, DELTA_Y, DELTA_Z, ROTATE_DELTA);
	public static final StreamCodec<ByteStream, Set<Relative>> SET_STREAM_CODEC = CommonStreamCodecs.INT.map(Relative::unpack, Relative::pack);
	private final int bit;

	Relative(int bit) {
		this.bit = bit;
	}

	private int getMask() {
		return 1 << this.bit;
	}

	private boolean isSet(int value) {
		return (value & this.getMask()) == this.getMask();
	}

	public static Set<Relative> unpack(final int value) {
		final Set<Relative> result = EnumSet.noneOf(Relative.class);
		for (final Relative relative : values()) {
			if (relative.isSet(value)) {
				result.add(relative);
			}
		}

		return result;
	}

	public static int pack(final Set<Relative> set) {
		int result = 0;
		for (final Relative relative : set) {
			result |= relative.getMask();
		}

		return result;
	}
}
