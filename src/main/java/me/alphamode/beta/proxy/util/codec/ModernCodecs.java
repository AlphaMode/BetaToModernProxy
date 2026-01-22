package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.Mth;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.text.TextComponent;
import net.lenni0451.mcstructs.text.serializer.TextComponentCodec;
import net.raphimc.netminecraft.netty.crypto.CryptUtil;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.security.PublicKey;
import java.time.Instant;
import java.util.*;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public interface ModernCodecs {
	short MAX_STRING_LENGTH = 32767;

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

	static int count(ByteBuf buf, final int max) {
		final int count = VAR_INT.decode(buf);
		if (count > max) {
			throw new RuntimeException("Element count exceeds max size");
		} else {
			return count;
		}
	}

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

	StreamCodec<ByteBuf, PublicKey> PUBLIC_KEY = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final PublicKey key) {
			BYTE_ARRAY.encode(buf, key.getEncoded());
		}

		@Override
		public PublicKey decode(final ByteBuf buf) {
			try {
				return CryptUtil.decodeRsaPublicKey(sizedByteArray(512).decode(buf));
			} catch (final Exception exception) {
				throw new RuntimeException("Malformed public key bytes", exception);
			}
		}
	};

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
		public void encode(ByteBuf buf, NbtTag value) {
			PacketTypes.writeUnnamedTag(buf, value);
		}

		@Override
		public NbtTag decode(ByteBuf buf) {
			return PacketTypes.readUnnamedTag(buf);
		}
	};

	StreamCodec<ByteBuf, NbtTag> NAMED_TAG = new StreamCodec<>() {
		@Override
		public void encode(ByteBuf buf, NbtTag value) {
			PacketTypes.writeNamedTag(buf, value);
		}

		@Override
		public NbtTag decode(ByteBuf buf) {
			return PacketTypes.readNamedTag(buf);
		}
	};

	StreamCodec<ByteBuf, TextComponent> COMPONENT = new StreamCodec<>() {
		@Override
		public void encode(ByteBuf buf, TextComponent value) {
			PacketTypes.writeUnnamedTag(buf, TextComponentCodec.LATEST.serializeNbtTree(value));
		}

		@Override
		public TextComponent decode(ByteBuf buf) {
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

	static StreamCodec<ByteBuf, String> stringUtf8() {
		return stringUtf8(MAX_STRING_LENGTH);
	}

	static <T> StreamCodec<ByteBuf, T> idMapper(final IntFunction<T> byId, final ToIntFunction<T> toId) {
		return new StreamCodec<>() {
			public T decode(final ByteBuf buf) {
				int id = VAR_INT.decode(buf);
				return byId.apply(id);
			}

			public void encode(final ByteBuf buf, final T value) {
				int id = toId.applyAsInt(value);
				VAR_INT.encode(buf, id);
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

	static <T> StreamCodec<ByteBuf, Collection<T>> collection(final StreamCodec<ByteBuf, T> codec) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final Collection<T> collection) {
				VAR_INT.encode(buf, collection.size());
				for (final T value : collection) {
					codec.encode(buf, value);
				}
			}

			@Override
			public Collection<T> decode(final ByteBuf buf) {
				final int count = VAR_INT.decode(buf);
				final Collection<T> collection = new ArrayList<>();
				for (int i = 0; i < count; ++i) {
					collection.add(codec.decode(buf));
				}

				return collection;
			}
		};
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
}
