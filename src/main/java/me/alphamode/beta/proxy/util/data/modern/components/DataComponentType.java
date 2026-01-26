package me.alphamode.beta.proxy.util.data.modern.components;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

public interface DataComponentType<T> {
	static <T> DataComponentType.Builder<T> builder() {
		return new DataComponentType.Builder<>();
	}

	StreamCodec<? super ByteBuf, T> streamCodec();

	class Builder<T> {
		private @Nullable StreamCodec<? super ByteBuf, T> streamCodec;

		public DataComponentType.Builder<T> networkSynchronized(StreamCodec<? super ByteBuf, T> streamCodec) {
			this.streamCodec = streamCodec;
			return this;
		}

		public DataComponentType<T> build() {
			return new DataComponentType.Builder.SimpleType<>(this.streamCodec);
		}

		private record SimpleType<T>(StreamCodec<? super ByteBuf, T> streamCodec) implements DataComponentType<T> {
		}
	}
}
