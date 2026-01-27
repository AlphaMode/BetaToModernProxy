package me.alphamode.beta.proxy.util.data.modern.components;

import org.jetbrains.annotations.Nullable;

public interface DataComponentGetter {
	<T> @Nullable T get(final DataComponentType<? extends T> type);

	default <T> T getOrDefault(final DataComponentType<? extends T> type, final T defaultValue) {
		T value = this.get(type);
		return value != null ? value : defaultValue;
	}

	default <T> @Nullable TypedDataComponent<T> getTyped(final DataComponentType<T> type) {
		T value = this.get(type);
		return value != null ? new TypedDataComponent<>(type, value) : null;
	}
}
