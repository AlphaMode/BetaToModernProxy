package me.alphamode.beta.proxy.util.codec;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Lists;
import com.google.gson.*;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.Mth;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.raphimc.netminecraft.netty.crypto.CryptUtil;
import net.raphimc.netminecraft.packet.PacketTypes;
import org.jspecify.annotations.Nullable;

import java.security.Key;
import java.security.PublicKey;
import java.time.Instant;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public interface ModernStreamCodecs {
	short MAX_STRING_LENGTH = 32767;

	StreamCodec<ByteStream, UUID> UUID = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final UUID value) {
			PacketTypes.writeUuid((ByteBuf) buf, value);
		}

		@Override
		public UUID decode(final ByteStream buf) {
			return PacketTypes.readUuid((ByteBuf) buf);
		}
	};

	StreamCodec<ByteStream, PropertyMap> GAME_PROFILE_PROPERTIES = new StreamCodec<>() {
		public PropertyMap decode(final ByteStream input) {
			final int propertyCount = readCount(input, 16);
			ImmutableMultimap.Builder<String, Property> result = ImmutableMultimap.builder();
			for (int i = 0; i < propertyCount; i++) {
				final String name = PacketTypes.readString((ByteBuf) input, 64);
				final String value = PacketTypes.readString((ByteBuf) input, MAX_STRING_LENGTH);
				final String signature = readNullable(input, in -> PacketTypes.readString((ByteBuf) in, 1024));

				final Property property = new Property(name, value, signature);
				result.put(property.name(), property);
			}

			return new PropertyMap(result.build());
		}

		public void encode(final ByteStream output, final PropertyMap properties) {
			writeCount(output, properties.size(), 16);
			for (final Property property : properties.values()) {
				PacketTypes.writeString((ByteBuf) output, property.name());
				PacketTypes.writeString((ByteBuf) output, property.value());
				writeNullable(output, property.signature(), (buf, value) -> PacketTypes.writeString((ByteBuf) buf, value));
			}
		}
	};

	StreamCodec<ByteStream, GameProfile> GAME_PROFILE = StreamCodec.composite(
			UUID,
			GameProfile::id,
			stringUtf8(16),
			GameProfile::name,
			GAME_PROFILE_PROPERTIES, GameProfile::properties,
			GameProfile::new
	);

	StreamCodec<ByteStream, byte[]> BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(final ByteStream buf) {
			final byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			return data;
		}

		@Override
		public void encode(final ByteStream buf, final byte[] value) {
			buf.writeBytes(value);
		}
	};

	StreamCodec<ByteStream, byte[]> PREFIXED_BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(final ByteStream buf) {
			final byte[] data = new byte[VAR_INT.decode(buf)];
			buf.readBytes(data);
			return data;
		}

		@Override
		public void encode(final ByteStream buf, final byte[] value) {
			VAR_INT.encode(buf, value.length);
			buf.writeBytes(value);
		}
	};

	StreamCodec<ByteStream, long[]> LONG_ARRAY = new StreamCodec<>() {
		@Override
		public long[] decode(final ByteStream buf) {
			final int size = VAR_INT.decode(buf);
			final int maxSize = buf.readableBytes() / 8;
			if (size > maxSize) {
				throw new RuntimeException("LongArray with size " + size + " is bigger than allowed " + maxSize);
			} else {
				final long[] data = new long[size];
				for (int i = 0; i < size; ++i) {
					data[i] = buf.readLong();
				}

				return data;
			}
		}

		@Override
		public void encode(final ByteStream buf, final long[] value) {
			VAR_INT.encode(buf, value.length);
			for (final long lol : value) {
				buf.writeLong(lol);
			}
		}
	};

	static StreamCodec<ByteStream, long[]> fixedLongArray(int size) {
		return new StreamCodec<>() {
			@Override
			public void encode(ByteStream buf, long[] value) {
				for (final long v : value) {
					buf.writeLong(v);
				}
			}

			@Override
			public long[] decode(ByteStream buf) {
				long[] data = new long[size];
				for (int i = 0; i < size; i++) {
					data[i] = buf.readLong();
				}

				return data;
			}
		};
	}

	StreamCodec<ByteStream, BitSet> BIT_SET = new StreamCodec<>() {
		@Override
		public BitSet decode(final ByteStream buf) {
			return BitSet.valueOf(LONG_ARRAY.decode(buf));
		}

		@Override
		public void encode(final ByteStream buf, final BitSet value) {
			LONG_ARRAY.encode(buf, value.toLongArray());
		}
	};

	static StreamCodec<ByteStream, byte[]> sizedByteArray(final int size) {
		return new StreamCodec<>() {
			@Override
			public byte[] decode(final ByteStream buf) {
				final byte[] data = new byte[size];
				buf.readBytes(data);
				return data;
			}

			@Override
			public void encode(final ByteStream buf, final byte[] value) {
				buf.writeBytes(value);
			}
		};
	}

	static StreamCodec<ByteStream, byte[]> byteArray(final int maxSize) {
		return new StreamCodec<>() {
			@Override
			public byte[] decode(final ByteStream buf) {
				final int size = VAR_INT.decode(buf);
				if (size > maxSize) {
					throw new RuntimeException("ByteArray with size " + size + " is bigger than allowed " + maxSize);
				} else {
					final byte[] data = new byte[size];
					buf.readBytes(data);
					return data;
				}
			}

			@Override
			public void encode(final ByteStream buf, final byte[] value) {
				VAR_INT.encode(buf, value.length);
				buf.writeBytes(value);
			}
		};
	}

	StreamCodec<ByteStream, Integer> VAR_INT = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final Integer value) {
			PacketTypes.writeVarInt((ByteBuf) buf, value);
		}

		@Override
		public Integer decode(final ByteStream buf) {
			return PacketTypes.readVarInt((ByteBuf) buf);
		}
	};

	StreamCodec<ByteStream, Long> VAR_LONG = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final Long value) {
			PacketTypes.writeVarLong((ByteBuf) buf, value);
		}

		@Override
		public Long decode(final ByteStream buf) {
			return PacketTypes.readVarLong((ByteBuf) buf);
		}
	};

	StreamCodec<ByteStream, IntList> INT_LIST = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final IntList value) {
			VAR_INT.encode(buf, value.size());
			for (int i = 0; i < value.size(); i++) {
				VAR_INT.encode(buf, value.getInt(i));
			}
		}

		@Override
		public IntList decode(final ByteStream buf) {
			final int count = VAR_INT.decode(buf);
			final IntList list = new IntArrayList();
			for (int i = 0; i < count; ++i) {
				list.add(VAR_INT.decode(buf));
			}

			return list;
		}
	};

	static <T, B extends ByteStream> void writeNullable(final B buf, @Nullable final T value, final StreamEncoder<? super B, T> encoder) {
		if (value != null) {
			buf.writeBoolean(true);
			encoder.encode(buf, value);
		} else {
			buf.writeBoolean(false);
		}
	}

	@Nullable
	static <T, B extends ByteStream> T readNullable(final B buf, final StreamDecoder<? super B, T> valueDecoder) {
		return buf.readBoolean() ? valueDecoder.decode(buf) : null;
	}

	static void writeCount(final ByteStream buf, final int value, final int maxSize) {
		if (value > maxSize) {
			throw new RuntimeException("Element count exceeds max size");
		} else {
			VAR_INT.encode(buf, value);
		}
	}

	static int readCount(final ByteStream buf, final int max) {
		final int count = VAR_INT.decode(buf);
		if (count > max) {
			throw new RuntimeException("Element count exceeds max size");
		} else {
			return count;
		}
	}

	StreamCodec<ByteStream, Instant> INSTANT = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final Instant value) {
			buf.writeLong(value.toEpochMilli());
		}

		@Override
		public Instant decode(final ByteStream buf) {
			return Instant.ofEpochMilli(buf.readLong());
		}
	};

	static StreamCodec<ByteStream, String> stringUtf8(final int maxLength) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final String value) {
				PacketTypes.writeString((ByteBuf) buf, value);
			}

			@Override
			public String decode(final ByteStream buf) {
				return PacketTypes.readString((ByteBuf) buf, maxLength);
			}
		};
	}

	StreamCodec<ByteStream, String> STRING_UTF8 = stringUtf8(MAX_STRING_LENGTH);

	StreamCodec<ByteStream, PublicKey> PUBLIC_KEY = byteArray(512).map(CryptUtil::decodeRsaPublicKey, Key::getEncoded);

	StreamCodec<ByteStream, Identifier> IDENTIFIER = STRING_UTF8.map(Identifier::of, Identifier::toString);

	StreamCodec<ByteStream, NbtTag> TAG = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final NbtTag value) {
			PacketTypes.writeUnnamedTag((ByteBuf) buf, value);
		}

		@Override
		public NbtTag decode(final ByteStream buf) {
			return PacketTypes.readUnnamedTag((ByteBuf) buf);
		}
	};

	StreamCodec<ByteStream, NbtTag> NAMED_TAG = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final NbtTag value) {
			PacketTypes.writeNamedTag((ByteBuf) buf, value);
		}

		@Override
		public NbtTag decode(final ByteStream buf) {
			return PacketTypes.readNamedTag((ByteBuf) buf);
		}
	};

	StreamCodec<ByteStream, TextComponent> TEXT_COMPONENT = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream buf, final TextComponent value) {
			PacketTypes.writeUnnamedTag((ByteBuf) buf, TextComponentCodec.LATEST.serializeNbtTree(value));
		}

		@Override
		public TextComponent decode(final ByteStream buf) {
			return TextComponentCodec.LATEST.deserialize(PacketTypes.readUnnamedTag((ByteBuf) buf));
		}
	};

	static <T> StreamCodec<ByteStream, T> idMapper(final IntFunction<T> byId, final ToIntFunction<T> toId) {
		return new StreamCodec<>() {
			public T decode(final ByteStream buf) {
				return byId.apply(VAR_INT.decode(buf));
			}

			public void encode(final ByteStream buf, final T value) {
				VAR_INT.encode(buf, toId.applyAsInt(value));
			}
		};
	}

	static <T> StreamCodec<ByteStream, T> nullable(final StreamCodec<ByteStream, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final T value) {
				if (value == null) {
					buf.writeBoolean(false);
				} else {
					buf.writeBoolean(true);
					codec.encode(buf, value);
				}
			}

			@Override
			public T decode(final ByteStream buf) {
				return buf.readBoolean() ? codec.decode(buf) : null;
			}
		};
	}

	static <T, S extends T> StreamCodec<ByteStream, Optional<S>> optional(final StreamCodec<ByteStream, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final Optional<S> value) {
				if (value.isEmpty()) {
					buf.writeBoolean(false);
				} else {
					buf.writeBoolean(true);
					codec.encode(buf, value.get());
				}
			}

			@Override
			public Optional<S> decode(final ByteStream buf) {
				return buf.readBoolean() ? Optional.of((S) codec.decode(buf)) : Optional.empty();
			}
		};
	}

	static StreamCodec<ByteStream, BitSet> sizedBitSet(final int size) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final BitSet bitSet) {
				if (bitSet.length() > size) {
					throw new RuntimeException("BitSet is larger than expected size (" + bitSet.length() + ">" + size + ")");
				} else {
					buf.writeBytes(Arrays.copyOf(bitSet.toByteArray(), Mth.positiveCeilDiv(size, 8)));
				}
			}

			@Override
			public BitSet decode(final ByteStream buf) {
				final byte[] bytes = new byte[Mth.positiveCeilDiv(size, 8)];
				buf.readBytes(bytes);
				return BitSet.valueOf(bytes);
			}
		};
	}

	static <B extends ByteStream, V, C extends Collection<V>> StreamCodec<B, C> collection(final IntFunction<C> constructor, final StreamCodec<? super B, V> elementCodec, final int maxSize) {
		return new StreamCodec<>() {
			public C decode(B input) {
				return readCollection(input, constructor, elementCodec);
			}

			public void encode(B output, C value) {
				writeCollection(output, value, elementCodec);
			}
		};
	}

	static <T, V extends Collection<T>> StreamCodec<ByteStream, V> collection(final StreamCodec<ByteStream, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final V collection) {
				VAR_INT.encode(buf, collection.size());
				for (final T value : collection) {
					codec.encode(buf, value);
				}
			}

			@Override
			public V decode(final ByteStream buf) {
				final int count = VAR_INT.decode(buf);
				final V collection = (V) new ArrayList<V>();
				for (int i = 0; i < count; ++i) {
					collection.add(codec.decode(buf));
				}

				return collection;
			}
		};
	}

	static <V> StreamCodec.CodecOperation<ByteStream, V, List<V>> list() {
		return ModernStreamCodecs::collection;
	}

	static <T> StreamCodec<ByteStream, List<T>> list(final StreamCodec<ByteStream, T> codec) {
		final StreamCodec<ByteStream, Collection<T>> collectionCodec = collection(codec);
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final List<T> list) {
				collectionCodec.encode(buf, list);
			}

			@Override
			public List<T> decode(final ByteStream buf) {
				return List.copyOf(collectionCodec.decode(buf));
			}
		};
	}

	static <B extends ByteStream, K, V, M extends Map<K, V>> StreamCodec<B, M> map(IntFunction<? extends M> constructor, StreamCodec<? super B, K> keyCodec, StreamCodec<? super B, V> valueCodec) {
		return map(constructor, keyCodec, valueCodec, Integer.MAX_VALUE);
	}

	static <B extends ByteStream, K, V, M extends Map<K, V>> StreamCodec<B, M> map(final IntFunction<? extends M> constructor, final StreamCodec<? super B, K> keyCodec, final StreamCodec<? super B, V> valueCodec, final int maxSize) {
		return new StreamCodec<>() {
			public void encode(B output, M map) {
				writeCount(output, map.size(), maxSize);
				for (final var entry : map.entrySet()) {
					keyCodec.encode(output, entry.getKey());
					valueCodec.encode(output, entry.getValue());
				}
			}

			public M decode(B input) {
				final int count = readCount(input, maxSize);

				final M result = constructor.apply(Math.min(count, 65536));
				for (int i = 0; i < count; i++) {
					result.put(keyCodec.decode(input), valueCodec.decode(input));
				}

				return result;
			}
		};
	}

	static <T, S> StreamCodec<ByteStream, Map<T, S>> javaMap(final StreamCodec<ByteStream, T> keyCodec, final StreamCodec<ByteStream, S> valueCodec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final Map<T, S> map) {
				VAR_INT.encode(buf, map.size());
				for (final var entry : map.entrySet()) {
					keyCodec.encode(buf, entry.getKey());
					valueCodec.encode(buf, entry.getValue());
				}
			}

			@Override
			public Map<T, S> decode(final ByteStream buf) {
				final int count = VAR_INT.decode(buf);
				final Map<T, S> map = new HashMap<>();
				for (int i = 0; i < count; ++i) {
					map.put(keyCodec.decode(buf), valueCodec.decode(buf));
				}

				return map;
			}
		};
	}

	static <T extends JsonElement> StreamCodec<ByteStream, T> lenientJson(final int maxLength) {
		return new StreamCodec<>() {
			private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

			@Override
			public T decode(final ByteStream buf) {
				try {
					return (T) JsonParser.parseString(stringUtf8(maxLength).decode(buf));
				} catch (JsonSyntaxException e) {
					throw new RuntimeException("Failed to parse JSON", e);
				}
			}

			@Override
			public void encode(final ByteStream buf, final T value) {
				stringUtf8(maxLength).encode(buf, GSON.toJson(value));
			}
		};
	}

	static <T extends Enum<T>> StreamCodec<ByteStream, T> javaEnum(final Class<T> enumClazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final T value) {
				VAR_INT.encode(buf, value.ordinal());
			}

			@Override
			public T decode(final ByteStream buf) {
				return enumClazz.getEnumConstants()[VAR_INT.decode(buf)];
			}
		};
	}

	static <T extends Enum<T>> StreamCodec<ByteStream, EnumSet<T>> enumSet(final Class<T> enumClazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final EnumSet<T> value) {
				writeEnumSet(buf, value, enumClazz);
			}

			@Override
			public EnumSet<T> decode(final ByteStream buf) {
				return readEnumSet(buf, enumClazz);
			}
		};
	}

	static <E extends Enum<E>> void writeEnumSet(final ByteStream buf, final EnumSet<E> set, final Class<E> clazz) {
		E[] values = clazz.getEnumConstants();
		BitSet mask = new BitSet(values.length);

		for (int i = 0; i < values.length; i++) {
			mask.set(i, set.contains(values[i]));
		}

		writeFixedBitSet(buf, mask, values.length);
	}

	static <E extends Enum<E>> EnumSet<E> readEnumSet(final ByteStream buf, final Class<E> clazz) {
		E[] values = clazz.getEnumConstants();
		BitSet mask = readFixedBitSet(buf, values.length);
		EnumSet<E> result = EnumSet.noneOf(clazz);

		for (int i = 0; i < values.length; i++) {
			if (mask.get(i)) {
				result.add(values[i]);
			}
		}

		return result;
	}

	static BitSet readFixedBitSet(final ByteStream buf, final int size) {
		byte[] bytes = new byte[Mth.positiveCeilDiv(size, 8)];
		buf.readBytes(bytes);
		return BitSet.valueOf(bytes);
	}

	static void writeFixedBitSet(final ByteStream buf, final BitSet bitSet, final int size) {
		if (bitSet.length() > size) {
			throw new EncoderException("BitSet is larger than expected size (" + bitSet.length() + ">" + size + ")");
		} else {
			byte[] bytes = bitSet.toByteArray();
			buf.writeBytes(Arrays.copyOf(bytes, Mth.positiveCeilDiv(size, 8)));
		}
	}

	static <T> List<T> readList(final ByteStream buf, final StreamDecoder<? super ByteStream, T> elementDecoder) {
		return readCollection(buf, Lists::newArrayListWithCapacity, elementDecoder);
	}

	static <B extends ByteStream, T, C extends Collection<T>> C readCollection(final B buf, final IntFunction<C> ctor, final StreamDecoder<? super B, T> elementDecoder) {
		int count = VAR_INT.decode(buf);
		C result = ctor.apply(count);

		for (int i = 0; i < count; i++) {
			result.add(elementDecoder.decode(buf));
		}

		return result;
	}

	static <B extends ByteStream, T> void writeCollection(final B buf, final Collection<T> collection, final StreamEncoder<? super B, T> encoder) {
		VAR_INT.encode(buf, collection.size());
		for (final T element : collection) {
			encoder.encode(buf, element);
		}
	}
}
