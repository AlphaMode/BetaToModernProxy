package me.alphamode.beta.proxy.util.codec;

import java.util.function.BiFunction;
import java.util.function.Function;

public interface StreamCodec<B, V> {
    void encode(B output, V value);

    V decode(B input);

    static <B, C, T1> StreamCodec<B, C> composite(
            final StreamCodec<? super B, T1> codec1,
            final Function<C, T1> getter1,
            final Function<T1, C> constructor
    ) {
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                return constructor.apply(v1);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                return constructor.apply(v1, v2);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                return constructor.apply(v1, v2, v3);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                return constructor.apply(v1, v2, v3, v4);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                T7 v7 = codec7.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6, v7);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
                codec7.encode(output, getter7.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                T7 v7 = codec7.decode(input);
                T8 v8 = codec8.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6, v7, v8);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
                codec7.encode(output, getter7.apply(value));
                codec8.encode(output, getter8.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                T7 v7 = codec7.decode(input);
                T8 v8 = codec8.decode(input);
                T9 v9 = codec9.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6, v7, v8, v9);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
                codec7.encode(output, getter7.apply(value));
                codec8.encode(output, getter8.apply(value));
                codec9.encode(output, getter9.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                T7 v7 = codec7.decode(input);
                T8 v8 = codec8.decode(input);
                T9 v9 = codec9.decode(input);
                T10 v10 = codec10.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
                codec7.encode(output, getter7.apply(value));
                codec8.encode(output, getter8.apply(value));
                codec9.encode(output, getter9.apply(value));
                codec10.encode(output, getter10.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                T7 v7 = codec7.decode(input);
                T8 v8 = codec8.decode(input);
                T9 v9 = codec9.decode(input);
                T10 v10 = codec10.decode(input);
                T11 v11 = codec11.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
                codec7.encode(output, getter7.apply(value));
                codec8.encode(output, getter8.apply(value));
                codec9.encode(output, getter9.apply(value));
                codec10.encode(output, getter10.apply(value));
                codec11.encode(output, getter11.apply(value));
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
        return new StreamCodec<B, C>() {
            @Override
            public C decode(final B input) {
                T1 v1 = codec1.decode(input);
                T2 v2 = codec2.decode(input);
                T3 v3 = codec3.decode(input);
                T4 v4 = codec4.decode(input);
                T5 v5 = codec5.decode(input);
                T6 v6 = codec6.decode(input);
                T7 v7 = codec7.decode(input);
                T8 v8 = codec8.decode(input);
                T9 v9 = codec9.decode(input);
                T10 v10 = codec10.decode(input);
                T11 v11 = codec11.decode(input);
                T12 v12 = codec12.decode(input);
                return constructor.apply(v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12);
            }

            @Override
            public void encode(final B output, final C value) {
                codec1.encode(output, getter1.apply(value));
                codec2.encode(output, getter2.apply(value));
                codec3.encode(output, getter3.apply(value));
                codec4.encode(output, getter4.apply(value));
                codec5.encode(output, getter5.apply(value));
                codec6.encode(output, getter6.apply(value));
                codec7.encode(output, getter7.apply(value));
                codec8.encode(output, getter8.apply(value));
                codec9.encode(output, getter9.apply(value));
                codec10.encode(output, getter10.apply(value));
                codec11.encode(output, getter11.apply(value));
                codec12.encode(output, getter12.apply(value));
            }
        };
    }
}
