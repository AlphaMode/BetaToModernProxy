package me.alphamode.beta.proxy.util.data.modern.components;

import java.util.Map;

public record TypedDataComponent<T>(DataComponentType<T> type, T value) {
	static TypedDataComponent<?> fromEntryUnchecked(final Map.Entry<DataComponentType<?>, Object> entry) {
		return createUnchecked(entry.getKey(), entry.getValue());
	}

	public static <T> TypedDataComponent<T> createUnchecked(final DataComponentType<T> type, final Object value) {
		return new TypedDataComponent<>(type, (T) value);
	}
}
