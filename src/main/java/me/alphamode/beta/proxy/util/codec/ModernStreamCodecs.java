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
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.data.modern.VarLong;
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

	StreamCodec<ByteBuf, String> PLAYER_NAME = stringUtf8(16);

	StreamCodec<ByteBuf, UUID> UUID = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final UUID value) {
			PacketTypes.writeUuid(buf, value);
		}

		@Override
		public UUID decode(final ByteBuf buf) {
			return PacketTypes.readUuid(buf);
		}
	};

	StreamCodec<ByteBuf, PropertyMap> GAME_PROFILE_PROPERTIES = new StreamCodec<>() {
		public PropertyMap decode(final ByteBuf input) {
			int propertyCount = readCount(input, 16);
			ImmutableMultimap.Builder<String, Property> result = ImmutableMultimap.builder();

			for (int i = 0; i < propertyCount; i++) {
				String name = PacketTypes.readString(input, 64);
				String value = PacketTypes.readString(input, MAX_STRING_LENGTH);
				String signature = readNullable(input, in -> PacketTypes.readString(in, 1024));
				Property property = new Property(name, value, signature);
				result.put(property.name(), property);
			}

			return new PropertyMap(result.build());
		}

		public void encode(final ByteBuf output, final PropertyMap properties) {
			writeCount(output, properties.size(), 16);

			for (Property property : properties.values()) {
				PacketTypes.writeString(output, property.name());
				PacketTypes.writeString(output, property.value());
				writeNullable(output, property.signature(), PacketTypes::writeString);
			}
		}
	};

	StreamCodec<ByteBuf, GameProfile> GAME_PROFILE = StreamCodec.composite(
			UUID, GameProfile::id,
			PLAYER_NAME, GameProfile::name,
			GAME_PROFILE_PROPERTIES, GameProfile::properties,
			GameProfile::new
	);

	StreamCodec<ByteBuf, byte[]> BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(final ByteBuf buf) {
			final byte[] data = new byte[buf.readableBytes()];
			buf.readBytes(data);
			return data;
		}

		@Override
		public void encode(final ByteBuf buf, final byte[] value) {
			buf.writeBytes(value);
		}
	};

	StreamCodec<ByteBuf, byte[]> PREFIXED_BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(final ByteBuf buf) {
			final byte[] data = new byte[VAR_INT.decode(buf)];
			buf.readBytes(data);
			return data;
		}

		@Override
		public void encode(final ByteBuf buf, final byte[] value) {
			VAR_INT.encode(buf, value.length);
			buf.writeBytes(value);
		}
	};

	StreamCodec<ByteBuf, long[]> LONG_ARRAY = new StreamCodec<>() {
		@Override
		public long[] decode(final ByteBuf buf) {
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
		public void encode(final ByteBuf buf, final long[] value) {
			VAR_INT.encode(buf, value.length);
			for (final long lol : value) {
				buf.writeLong(lol);
			}
		}
	};

	static StreamCodec<ByteBuf, long[]> fixedLongArray(int size) {
		return new StreamCodec<>() {
			@Override
			public void encode(ByteBuf buf, long[] value) {
				for (final long v : value) {
					buf.writeLong(v);
				}
			}

			@Override
			public long[] decode(ByteBuf buf) {
				long[] data = new long[size];
				for (int i = 0; i < size; i++) {
					data[i] = buf.readLong();
				}

				return data;
			}
		};
	}

	StreamCodec<ByteBuf, BitSet> BIT_SET = new StreamCodec<>() {
		@Override
		public BitSet decode(final ByteBuf buf) {
			return BitSet.valueOf(LONG_ARRAY.decode(buf));
		}

		@Override
		public void encode(final ByteBuf buf, final BitSet value) {
			LONG_ARRAY.encode(buf, value.toLongArray());
		}
	};

	static StreamCodec<ByteBuf, byte[]> sizedByteArray(final int size) {
		return new StreamCodec<>() {
			@Override
			public byte[] decode(final ByteBuf buf) {
				final byte[] data = new byte[size];
				buf.readBytes(data);
				return data;
			}

			@Override
			public void encode(final ByteBuf buf, final byte[] value) {
				buf.writeBytes(value);
			}
		};
	}

	static StreamCodec<ByteBuf, byte[]> byteArray(final int maxSize) {
		return new StreamCodec<>() {
			@Override
			public byte[] decode(final ByteBuf buf) {
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
			public void encode(final ByteBuf buf, final byte[] value) {
				VAR_INT.encode(buf, value.length);
				buf.writeBytes(value);
			}
		};
	}

	StreamCodec<ByteBuf, Integer> VAR_INT = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Integer value) {
			PacketTypes.writeVarInt(buf, value);
		}

		@Override
		public Integer decode(final ByteBuf buf) {
			return PacketTypes.readVarInt(buf);
		}
	};

	StreamCodec<ByteBuf, Long> VAR_LONG = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Long value) {
			VarLong.write(buf, value);
		}

		@Override
		public Long decode(final ByteBuf buf) {
			return VarLong.read(buf);
		}
	};

	StreamCodec<ByteBuf, IntList> INT_LIST = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final IntList value) {
			VAR_INT.encode(buf, value.size());
			for (int i = 0; i < value.size(); i++) {
				VAR_INT.encode(buf, value.getInt(i));
			}
		}

		@Override
		public IntList decode(final ByteBuf buf) {
			final int count = VAR_INT.decode(buf);
			final IntList list = new IntArrayList();
			for (int i = 0; i < count; ++i) {
				list.add(VAR_INT.decode(buf));
			}

			return list;
		}
	};

	static <T, B extends ByteBuf> void writeNullable(final B buf, @Nullable final T value, final StreamEncoder<? super B, T> encoder) {
		if (value != null) {
			buf.writeBoolean(true);
			encoder.encode(buf, value);
		} else {
			buf.writeBoolean(false);
		}
	}

	@Nullable
	static <T, B extends ByteBuf> T readNullable(final B buf, final StreamDecoder<? super B, T> valueDecoder) {
		return buf.readBoolean() ? valueDecoder.decode(buf) : null;
	}

	static void writeCount(final ByteBuf buf, final int value, final int maxSize) {
		if (value > maxSize) {
			throw new RuntimeException("Element count exceeds max size");
		} else {
			VAR_INT.encode(buf, value);
		}
	}

	static int readCount(final ByteBuf buf, final int max) {
		final int count = VAR_INT.decode(buf);
		if (count > max) {
			throw new RuntimeException("Element count exceeds max size");
		} else {
			return count;
		}
	}

	StreamCodec<ByteBuf, Instant> INSTANT = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Instant value) {
			buf.writeLong(value.toEpochMilli());
		}

		@Override
		public Instant decode(final ByteBuf buf) {
			return Instant.ofEpochMilli(buf.readLong());
		}
	};

	StreamCodec<ByteBuf, PublicKey> PUBLIC_KEY = byteArray(512).map(CryptUtil::decodeRsaPublicKey, Key::getEncoded);

	StreamCodec<ByteBuf, Identifier> IDENTIFIER = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Identifier value) {
			stringUtf8().encode(buf, value.toString());
		}

		@Override
		public Identifier decode(final ByteBuf buf) {
			return Identifier.of(stringUtf8().decode(buf));
		}
	};

	StreamCodec<ByteBuf, NbtTag> TAG = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final NbtTag value) {
			PacketTypes.writeUnnamedTag(buf, value);
		}

		@Override
		public NbtTag decode(final ByteBuf buf) {
			return PacketTypes.readUnnamedTag(buf);
		}
	};

	StreamCodec<ByteBuf, NbtTag> NAMED_TAG = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final NbtTag value) {
			PacketTypes.writeNamedTag(buf, value);
		}

		@Override
		public NbtTag decode(final ByteBuf buf) {
			return PacketTypes.readNamedTag(buf);
		}
	};

	StreamCodec<ByteBuf, TextComponent> TEXT_COMPONENT = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final TextComponent value) {
			PacketTypes.writeUnnamedTag(buf, TextComponentCodec.LATEST.serializeNbtTree(value));
		}

		@Override
		public TextComponent decode(final ByteBuf buf) {
			return TextComponentCodec.LATEST.deserialize(PacketTypes.readUnnamedTag(buf));
		}
	};

	static StreamCodec<ByteBuf, String> stringUtf8(final int maxLength) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final String value) {
				PacketTypes.writeString(buf, value);
			}

			@Override
			public String decode(final ByteBuf buf) {
				return PacketTypes.readString(buf, maxLength);
			}
		};
	}

	StreamCodec<ByteBuf, String> STRING_UTF8 = stringUtf8();

	static StreamCodec<ByteBuf, String> stringUtf8() {
		return stringUtf8(MAX_STRING_LENGTH);
	}

	static <T> StreamCodec<ByteBuf, T> idMapper(final IntFunction<T> byId, final ToIntFunction<T> toId) {
		return new StreamCodec<>() {
			public T decode(final ByteBuf buf) {
				return byId.apply(VAR_INT.decode(buf));
			}

			public void encode(final ByteBuf buf, final T value) {
				VAR_INT.encode(buf, toId.applyAsInt(value));
			}
		};
	}

	static <T> StreamCodec<ByteBuf, T> nullable(final StreamCodec<ByteBuf, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final T value) {
				if (value == null) {
					buf.writeBoolean(false);
				} else {
					buf.writeBoolean(true);
					codec.encode(buf, value);
				}
			}

			@Override
			public T decode(final ByteBuf buf) {
				return buf.readBoolean() ? codec.decode(buf) : null;
			}
		};
	}

	static <T, S extends T> StreamCodec<ByteBuf, Optional<S>> optional(final StreamCodec<ByteBuf, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final Optional<S> value) {
				if (value.isEmpty()) {
					buf.writeBoolean(false);
				} else {
					buf.writeBoolean(true);
					codec.encode(buf, value.get());
				}
			}

			@Override
			public Optional<S> decode(final ByteBuf buf) {
				return buf.readBoolean() ? Optional.of((S) codec.decode(buf)) : Optional.empty();
			}
		};
	}

	static StreamCodec<ByteBuf, BitSet> sizedBitSet(final int size) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final BitSet bitSet) {
				if (bitSet.length() > size) {
					throw new RuntimeException("BitSet is larger than expected size (" + bitSet.length() + ">" + size + ")");
				} else {
					buf.writeBytes(Arrays.copyOf(bitSet.toByteArray(), Mth.positiveCeilDiv(size, 8)));
				}
			}

			@Override
			public BitSet decode(final ByteBuf buf) {
				final byte[] bytes = new byte[Mth.positiveCeilDiv(size, 8)];
				buf.readBytes(bytes);
				return BitSet.valueOf(bytes);
			}
		};
	}

	static <B extends ByteBuf, V, C extends Collection<V>> StreamCodec<B, C> collection(final IntFunction<C> constructor, final StreamCodec<? super B, V> elementCodec, final int maxSize) {
		return new StreamCodec<>() {
			public C decode(B input) {
				return readCollection(input, constructor, elementCodec);
			}

			public void encode(B output, C value) {
				writeCollection(output, value, elementCodec);
			}
		};
	}

	static <T, V extends Collection<T>> StreamCodec<ByteBuf, V> collection(final StreamCodec<ByteBuf, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final V collection) {
				VAR_INT.encode(buf, collection.size());
				for (final T value : collection) {
					codec.encode(buf, value);
				}
			}

			@Override
			public V decode(final ByteBuf buf) {
				final int count = VAR_INT.decode(buf);
				final V collection = (V) new ArrayList<V>();
				for (int i = 0; i < count; ++i) {
					collection.add(codec.decode(buf));
				}

				return collection;
			}
		};
	}

	static <V> StreamCodec.CodecOperation<ByteBuf, V, List<V>> list() {
		return ModernStreamCodecs::collection;
	}

	static <T> StreamCodec<ByteBuf, List<T>> list(final StreamCodec<ByteBuf, T> codec) {
		final StreamCodec<ByteBuf, Collection<T>> collectionCodec = collection(codec);
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final List<T> list) {
				collectionCodec.encode(buf, list);
			}

			@Override
			public List<T> decode(final ByteBuf buf) {
				return List.copyOf(collectionCodec.decode(buf));
			}
		};
	}

	static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> map(IntFunction<? extends M> constructor, StreamCodec<? super B, K> keyCodec, StreamCodec<? super B, V> valueCodec) {
		return map(constructor, keyCodec, valueCodec, Integer.MAX_VALUE);
	}

	static <B extends ByteBuf, K, V, M extends Map<K, V>> StreamCodec<B, M> map(final IntFunction<? extends M> constructor, final StreamCodec<? super B, K> keyCodec, final StreamCodec<? super B, V> valueCodec, final int maxSize) {
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

	static <T, S> StreamCodec<ByteBuf, Map<T, S>> javaMap(final StreamCodec<ByteBuf, T> keyCodec, final StreamCodec<ByteBuf, S> valueCodec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final Map<T, S> map) {
				VAR_INT.encode(buf, map.size());
				for (final var entry : map.entrySet()) {
					keyCodec.encode(buf, entry.getKey());
					valueCodec.encode(buf, entry.getValue());
				}
			}

			@Override
			public Map<T, S> decode(final ByteBuf buf) {
				final int count = VAR_INT.decode(buf);
				final Map<T, S> map = new HashMap<>();
				for (int i = 0; i < count; ++i) {
					map.put(keyCodec.decode(buf), valueCodec.decode(buf));
				}

				return map;
			}
		};
	}

	static <T extends JsonElement> StreamCodec<ByteBuf, T> lenientJson(final int maxLength) {
		return new StreamCodec<>() {
			private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

			@Override
			public T decode(final ByteBuf buf) {
				try {
					return (T) JsonParser.parseString(stringUtf8(maxLength).decode(buf));
				} catch (JsonSyntaxException e) {
					throw new RuntimeException("Failed to parse JSON", e);
				}
			}

			@Override
			public void encode(final ByteBuf buf, final T value) {
				stringUtf8(maxLength).encode(buf, GSON.toJson(value));
			}
		};
	}

	static <T extends Enum<T>> StreamCodec<ByteBuf, T> javaEnum(final Class<T> enumClazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final T value) {
				VAR_INT.encode(buf, value.ordinal());
			}

			@Override
			public T decode(final ByteBuf buf) {
				return enumClazz.getEnumConstants()[VAR_INT.decode(buf)];
			}
		};
	}

	static <T extends Enum<T>> StreamCodec<ByteBuf, EnumSet<T>> enumSet(final Class<T> enumClazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final EnumSet<T> value) {
				writeEnumSet(buf, value, enumClazz);
			}

			@Override
			public EnumSet<T> decode(final ByteBuf buf) {
				return readEnumSet(buf, enumClazz);
			}
		};
	}

	static <E extends Enum<E>> void writeEnumSet(final ByteBuf buf, final EnumSet<E> set, final Class<E> clazz) {
		E[] values = clazz.getEnumConstants();
		BitSet mask = new BitSet(values.length);

		for (int i = 0; i < values.length; i++) {
			mask.set(i, set.contains(values[i]));
		}

		writeFixedBitSet(buf, mask, values.length);
	}

	static <E extends Enum<E>> EnumSet<E> readEnumSet(final ByteBuf buf, final Class<E> clazz) {
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

	static BitSet readFixedBitSet(final ByteBuf buf, final int size) {
		byte[] bytes = new byte[Mth.positiveCeilDiv(size, 8)];
		buf.readBytes(bytes);
		return BitSet.valueOf(bytes);
	}

	static void writeFixedBitSet(final ByteBuf buf, final BitSet bitSet, final int size) {
		if (bitSet.length() > size) {
			throw new EncoderException("BitSet is larger than expected size (" + bitSet.length() + ">" + size + ")");
		} else {
			byte[] bytes = bitSet.toByteArray();
			buf.writeBytes(Arrays.copyOf(bytes, Mth.positiveCeilDiv(size, 8)));
		}
	}

	static <T> List<T> readList(final ByteBuf buf, final StreamDecoder<? super ByteBuf, T> elementDecoder) {
		return readCollection(buf, Lists::newArrayListWithCapacity, elementDecoder);
	}

	static <B extends ByteBuf, T, C extends Collection<T>> C readCollection(final B buf, final IntFunction<C> ctor, final StreamDecoder<? super B, T> elementDecoder) {
		int count = VAR_INT.decode(buf);
		C result = ctor.apply(count);

		for (int i = 0; i < count; i++) {
			result.add(elementDecoder.decode(buf));
		}

		return result;
	}

	static <B extends ByteBuf, T> void writeCollection(final B buf, final Collection<T> collection, final StreamEncoder<? super B, T> encoder) {
		VAR_INT.encode(buf, collection.size());
		for (final T element : collection) {
			encoder.encode(buf, element);
		}
	}
}
