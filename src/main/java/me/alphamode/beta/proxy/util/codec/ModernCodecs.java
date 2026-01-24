package me.alphamode.beta.proxy.util.codec;

import me.alphamode.beta.proxy.util.ARGB;
import net.lenni0451.mcstructs.converter.DataConverter;
import net.lenni0451.mcstructs.converter.codec.Codec;
import net.lenni0451.mcstructs.converter.mapcodec.MapCodec;
import net.lenni0451.mcstructs.converter.model.Result;

import java.util.HexFormat;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public interface ModernCodecs {
	Codec<Integer> STRING_RGB_COLOR = hexColor(6).map(ARGB::transparent, ARGB::opaque);
	Codec<Integer> STRING_ARGB_COLOR = hexColor(8);

	static <A> MapCodec<A> optional(final String name, final Codec<A> codec, final A defaultValue) {
		return optional(name, codec, defaultValue, false);
	}

	static <A> MapCodec<A> optional(final String name, final Codec<A> codec, final A defaultValue, final boolean lenient) {
		return optional(name, codec, lenient).map(
				a -> Objects.equals(a, defaultValue) ? Optional.empty() : Optional.of(a),
				o -> o.orElse(defaultValue)
		);
	}

	static <T> MapCodec<Optional<T>> optional(final String name, final Codec<T> codec) {
		return optional(name, codec, false);
	}

	static <T> MapCodec<Optional<T>> optional(final String name, final Codec<T> codec, final boolean lenient) {
		return new MapCodec<>() {
			@Override
			public <S> Result<Map<S, S>> serialize(DataConverter<S> converter, Map<S, S> map, Optional<T> element) {
				if (element.isEmpty()) {
					return Result.success(map);
				} else {
					final Result<S> result = codec.serialize(converter, element.get());
					if (result.isError()) {
						return result.mapError();
					} else {
						map.put(converter.createString(name), result.get());
						return Result.success(map);
					}
				}
			}

			@Override
			public <S> Result<Optional<T>> deserialize(DataConverter<S> converter, Map<S, S> map) {
				final S value = map.get(name);
				if (value == null) {
					return Result.success(Optional.empty());
				}

				final Result<T> result = codec.deserialize(converter, value);
				if (result.isError() && lenient) {
					return Result.success(Optional.empty());
				}

				return result.map(Optional::of);
			}
		};
	}

	static Codec<Integer> hexColor(final int expectedDigits) {
		final long maxValue = (1L << expectedDigits * 4) - 1L;
		return Codec.STRING.flatMap(integer -> Result.success("#" + HexFormat.of().toHexDigits(integer, expectedDigits)), string -> {
			if (!string.startsWith("#")) {
				return Result.error("Hex color must begin with #");
			} else {
				final int digits = string.length() - "#".length();
				if (digits != expectedDigits) {
					return Result.error("Hex color is wrong size, expected " + expectedDigits + " digits but got " + digits);
				} else {
					try {
						long value = HexFormat.fromHexDigitsToLong(string, "#".length(), string.length());
						return value >= 0L && value <= maxValue ? Result.success((int) value) : Result.error("Color value out of range: " + string);
					} catch (NumberFormatException var7) {
						return Result.error("Invalid color value: " + string);
					}
				}
			}
		});
	}
}
