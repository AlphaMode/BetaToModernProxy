package me.alphamode.beta.proxy.util.data.modern.components;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.converter.codec.Codec;
import org.jetbrains.annotations.Nullable;

public interface DataComponentType<T> {
	StreamCodec<ByteStream, DataComponentType<?>> REGISTRY_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final DataComponentType<?> value) {
			ModernStreamCodecs.VAR_INT.encode(stream, DataComponents.getRawId(value));
		}

		@Override
		public DataComponentType<?> decode(final ByteStream stream) {
			return DataComponents.byRawId(ModernStreamCodecs.VAR_INT.decode(stream));
		}
	};

	static <T> DataComponentType.Builder<T> builder() {
		return new DataComponentType.Builder<>();
	}

	@Nullable Codec<T> codec();

	default Codec<T> codecOrThrow() {
		final Codec<T> codec = this.codec();
		if (codec == null) {
			throw new IllegalStateException(this + " is not a persistent component");
		} else {
			return codec;
		}
	}

	StreamCodec<? super ByteStream, T> streamCodec();

	class Builder<T> {
		private @Nullable Codec<T> codec;
		private @Nullable StreamCodec<? super ByteStream, T> streamCodec;
		private boolean cacheEncoding;

		public DataComponentType.Builder<T> persistent(Codec<T> codec) {
			this.codec = codec;
			return this;
		}

		public DataComponentType.Builder<T> networkSynchronized(StreamCodec<? super ByteStream, T> streamCodec) {
			this.streamCodec = streamCodec;
			return this;
		}

		public DataComponentType.Builder<T> cacheEncoding() {
			this.cacheEncoding = true;
			return this;
		}

		public DataComponentType<T> build() {
			// TODO
			/*DataComponents.ENCODER_CACHE.wrap(this.codec)*/
			final Codec<T> cachingCodec = this.codec;
			return new DataComponentType.Builder.SimpleType<>(cachingCodec, this.streamCodec);
		}

		private record SimpleType<T>(@Nullable Codec<T> codec,
									 StreamCodec<? super ByteStream, T> streamCodec) implements DataComponentType<T> {
			@Override
			public String toString() {
				return DataComponents.getId(this).get();
			}
		}
	}
}
