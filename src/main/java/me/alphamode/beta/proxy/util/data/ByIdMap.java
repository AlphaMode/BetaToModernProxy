package me.alphamode.beta.proxy.util.data;

import me.alphamode.beta.proxy.util.Mth;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public interface ByIdMap {
	static <T> T[] createSortedArray(final ToIntFunction<T> idGetter, final T[] values) {
		final int length = values.length;
		if (length == 0) {
			throw new IllegalArgumentException("Empty value list");
		} else {
			final T[] result = values.clone();
			Arrays.fill(result, null);
			for (T value : values) {
				final int id = idGetter.applyAsInt(value);
				if (id < 0 || id >= length) {
					throw new IllegalArgumentException("Values are not continous, found index " + id + " for value " + value);
				}

				final T previous = result[id];
				if (previous != null) {
					throw new IllegalArgumentException("Duplicate entry on id " + id + ": current=" + value + ", previous=" + previous);
				}

				result[id] = value;
			}

			for (int i = 0; i < length; i++) {
				if (result[i] == null) {
					throw new IllegalArgumentException("Missing value at index: " + i);
				}
			}

			return result;
		}
	}

	static <T> IntFunction<T> continuous(final ToIntFunction<T> idGetter, final T[] values, final ByIdMap.OutOfBoundsStrategy strategy) {
		final T[] sortedValues = createSortedArray(idGetter, values);
		final int length = sortedValues.length;
		return switch (strategy) {
			case ZERO -> id -> id >= 0 && id < length ? sortedValues[id] : sortedValues[0];
			case WRAP -> id -> sortedValues[Mth.positiveModulo(id, length)];
			case CLAMP -> id -> sortedValues[Mth.clamp(id, 0, length - 1)];
		};
	}

	enum OutOfBoundsStrategy {
		ZERO,
		WRAP,
		CLAMP;
	}
}
