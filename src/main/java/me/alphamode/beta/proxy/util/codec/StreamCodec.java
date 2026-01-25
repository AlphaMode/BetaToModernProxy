package me.alphamode.beta.proxy.util.codec;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface StreamCodec<B, V> extends StreamEncoder<B, V>, StreamDecoder<B, V> {
	@Override
	void encode(B buf, V value);

	@Override
	V decode(B buf);

	static <B, V> StreamCodec<B, V> of(final StreamEncoder<B, V> encoder, final StreamDecoder<B, V> decoder) {
		return new StreamCodec<>() {
			@Override
			public V decode(final B buf) {
				return decoder.decode(buf);
			}

			@Override
			public void encode(final B buf, final V value) {
				encoder.encode(buf, value);
			}
		};
	}

	static <B, V> StreamCodec<B, V> ofMember(final StreamMemberEncoder<B, V> encoder, final StreamDecoder<B, V> decoder) {
		return new StreamCodec<>() {
			@Override
			public V decode(final B buf) {
				return decoder.decode(buf);
			}

			@Override
			public void encode(final B buf, final V value) {
				encoder.encode(value, buf);
			}
		};
	}

	static <B, V> StreamCodec<B, V> unit(final V instance) {
		return new StreamCodec<>() {
			@Override
			public V decode(final B buf) {
				return instance;
			}

			@Override
			public void encode(final B buf, final V value) {
				if (!value.equals(instance)) {
					throw new IllegalStateException("Can't encode '" + value + "', expected '" + instance + "'");
				}
			}
		};
	}

	default <O> StreamCodec<B, O> apply(StreamCodec.CodecOperation<B, V, O> operation) {
		return operation.apply(this);
	}

	default <O> StreamCodec<B, O> map(final Function<? super V, ? extends O> to, final Function<? super O, ? extends V> from) {
		return new StreamCodec<>() {
			@Override
			public O decode(final B input) {
				return to.apply(StreamCodec.this.decode(input));
			}

			@Override
			public void encode(final B buf, final O value) {
				StreamCodec.this.encode(buf, from.apply(value));
			}
		};
	}

	static <B, C, T1> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final Function<T1, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(codec1.decode(buf));
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
			}
		};
	}

	static <B, C, T1, T2> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final BiFunction<T1, T2, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(codec1.decode(buf), codec2.decode(buf));
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final Function3<T1, T2, T3, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(codec1.decode(buf), codec2.decode(buf), codec3.decode(buf));
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final Function4<T1, T2, T3, T4, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final Function5<T1, T2, T3, T4, T5, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final Function6<T1, T2, T3, T4, T5, T6, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6, T7> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final StreamCodec<? super B, T7> codec7,
			final Function<C, T7> getter7,
			final Function7<T1, T2, T3, T4, T5, T6, T7, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf),
						codec7.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
				codec7.encode(buf, getter7.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6, T7, T8> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final StreamCodec<? super B, T7> codec7,
			final Function<C, T7> getter7,
			final StreamCodec<? super B, T8> codec8,
			final Function<C, T8> getter8,
			final Function8<T1, T2, T3, T4, T5, T6, T7, T8, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf),
						codec7.decode(buf),
						codec8.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
				codec7.encode(buf, getter7.apply(value));
				codec8.encode(buf, getter8.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final StreamCodec<? super B, T7> codec7,
			final Function<C, T7> getter7,
			final StreamCodec<? super B, T8> codec8,
			final Function<C, T8> getter8,
			final StreamCodec<? super B, T9> codec9,
			final Function<C, T9> getter9,
			final Function9<T1, T2, T3, T4, T5, T6, T7, T8, T9, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf),
						codec7.decode(buf),
						codec8.decode(buf),
						codec9.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
				codec7.encode(buf, getter7.apply(value));
				codec8.encode(buf, getter8.apply(value));
				codec9.encode(buf, getter9.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final StreamCodec<? super B, T7> codec7,
			final Function<C, T7> getter7,
			final StreamCodec<? super B, T8> codec8,
			final Function<C, T8> getter8,
			final StreamCodec<? super B, T9> codec9,
			final Function<C, T9> getter9,
			final StreamCodec<? super B, T10> codec10,
			final Function<C, T10> getter10,
			final Function10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf),
						codec7.decode(buf),
						codec8.decode(buf),
						codec9.decode(buf),
						codec10.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
				codec7.encode(buf, getter7.apply(value));
				codec8.encode(buf, getter8.apply(value));
				codec9.encode(buf, getter9.apply(value));
				codec10.encode(buf, getter10.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final StreamCodec<? super B, T7> codec7,
			final Function<C, T7> getter7,
			final StreamCodec<? super B, T8> codec8,
			final Function<C, T8> getter8,
			final StreamCodec<? super B, T9> codec9,
			final Function<C, T9> getter9,
			final StreamCodec<? super B, T10> codec10,
			final Function<C, T10> getter10,
			final StreamCodec<? super B, T11> codec11,
			final Function<C, T11> getter11,
			final Function11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf),
						codec7.decode(buf),
						codec8.decode(buf),
						codec9.decode(buf),
						codec10.decode(buf),
						codec11.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
				codec7.encode(buf, getter7.apply(value));
				codec8.encode(buf, getter8.apply(value));
				codec9.encode(buf, getter9.apply(value));
				codec10.encode(buf, getter10.apply(value));
				codec11.encode(buf, getter11.apply(value));
			}
		};
	}

	static <B, C, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12> StreamCodec<B, C> composite(
			final StreamCodec<? super B, T1> codec1,
			final Function<C, T1> getter1,
			final StreamCodec<? super B, T2> codec2,
			final Function<C, T2> getter2,
			final StreamCodec<? super B, T3> codec3,
			final Function<C, T3> getter3,
			final StreamCodec<? super B, T4> codec4,
			final Function<C, T4> getter4,
			final StreamCodec<? super B, T5> codec5,
			final Function<C, T5> getter5,
			final StreamCodec<? super B, T6> codec6,
			final Function<C, T6> getter6,
			final StreamCodec<? super B, T7> codec7,
			final Function<C, T7> getter7,
			final StreamCodec<? super B, T8> codec8,
			final Function<C, T8> getter8,
			final StreamCodec<? super B, T9> codec9,
			final Function<C, T9> getter9,
			final StreamCodec<? super B, T10> codec10,
			final Function<C, T10> getter10,
			final StreamCodec<? super B, T11> codec11,
			final Function<C, T11> getter11,
			final StreamCodec<? super B, T12> codec12,
			final Function<C, T12> getter12,
			final Function12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, C> constructor
	) {
		return new StreamCodec<>() {
			@Override
			public C decode(final B buf) {
				return constructor.apply(
						codec1.decode(buf),
						codec2.decode(buf),
						codec3.decode(buf),
						codec4.decode(buf),
						codec5.decode(buf),
						codec6.decode(buf),
						codec7.decode(buf),
						codec8.decode(buf),
						codec9.decode(buf),
						codec10.decode(buf),
						codec11.decode(buf),
						codec12.decode(buf)
				);
			}

			@Override
			public void encode(final B buf, final C value) {
				codec1.encode(buf, getter1.apply(value));
				codec2.encode(buf, getter2.apply(value));
				codec3.encode(buf, getter3.apply(value));
				codec4.encode(buf, getter4.apply(value));
				codec5.encode(buf, getter5.apply(value));
				codec6.encode(buf, getter6.apply(value));
				codec7.encode(buf, getter7.apply(value));
				codec8.encode(buf, getter8.apply(value));
				codec9.encode(buf, getter9.apply(value));
				codec10.encode(buf, getter10.apply(value));
				codec11.encode(buf, getter11.apply(value));
				codec12.encode(buf, getter12.apply(value));
			}
		};
	}

	@FunctionalInterface
	interface CodecOperation<B, S, T> {
		StreamCodec<B, T> apply(StreamCodec<B, S> codec);
	}
}
