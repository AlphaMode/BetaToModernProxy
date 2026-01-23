package me.alphamode.beta.proxy.util;

import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.model.Result;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface StringRepresentable {
    int PRE_BUILT_MAP_THRESHOLD = 16;

    String getSerializedName();

    static <E extends Enum<E> & StringRepresentable> StringRepresentable.EnumCodec<E> fromEnum(final Supplier<E[]> values) {
        return fromEnumWithMapping(values, s -> s);
    }

    static <E extends Enum<E> & StringRepresentable> StringRepresentable.EnumCodec<E> fromEnumWithMapping(
            final Supplier<E[]> values, final Function<String, String> converter
    ) {
        final E[] valueArray = values.get();
        final Function<String, E> lookupFunction = createNameLookup(valueArray, e -> converter.apply(e.getSerializedName()));
        return new StringRepresentable.EnumCodec<>(lookupFunction);
    }

    static <T> Function<String, T> createNameLookup(final T[] valueArray, final Function<T, String> converter) {
        if (valueArray.length > PRE_BUILT_MAP_THRESHOLD) {
            final Map<String, T> byName = Arrays.stream(valueArray).collect(Collectors.toMap(converter, d -> d));
            return byName::get;
        } else {
            return id -> {
                for (final T value : valueArray) {
                    if (converter.apply(value).equals(id)) {
                        return value;
                    }
                }

                return null;
            };
        }
    }

    class EnumCodec<E extends Enum<E> & StringRepresentable> extends StringRepresentable.StringRepresentableCodec<E> {
        private final Function<String, E> resolver;

        public EnumCodec(final Function<String, E> nameResolver) {
            super(nameResolver);
            this.resolver = nameResolver;
        }

        @Nullable
        public E byName(final String name) {
            return this.resolver.apply(name);
        }

        public E byName(final String name, final E _default) {
            return Objects.requireNonNullElse(this.byName(name), _default);
        }

        public E byName(final String name, final Supplier<? extends E> defaultSupplier) {
            return Objects.requireNonNullElseGet(this.byName(name), defaultSupplier);
        }
    }

    class StringRepresentableCodec<S extends StringRepresentable> implements Codec<S> {
        private final Codec<S> codec;

        public StringRepresentableCodec(final Function<String, S> nameResolver) {
            this.codec = Codec.STRING.map(StringRepresentable::getSerializedName, nameResolver);
        }

        @Override
        public <S1> Result<S> deserialize(DataConverter<S1> converter, S1 data) {
            return this.codec.deserialize(converter, data);
        }

        @Override
        public <S1> Result<S1> serialize(DataConverter<S1> converter, S element) {
            return this.codec.serialize(converter, element);
        }
    }
}
