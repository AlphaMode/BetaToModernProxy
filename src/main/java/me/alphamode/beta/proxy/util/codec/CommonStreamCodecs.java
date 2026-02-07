package me.alphamode.beta.proxy.util.codec;

import me.alphamode.beta.proxy.util.ByteStream;

import java.lang.reflect.Array;

public interface CommonStreamCodecs {
	StreamCodec<ByteStream, Boolean> BOOL = new StreamCodec<>() {
		public Boolean decode(final ByteStream buf) {
			return buf.readBoolean();
		}

		public void encode(final ByteStream buf, final Boolean value) {
			buf.writeBoolean(value);
		}
	};

	StreamCodec<ByteStream, Byte> BYTE = new StreamCodec<>() {
		public Byte decode(final ByteStream buf) {
			return buf.readByte();
		}

		public void encode(final ByteStream buf, final Byte value) {
			buf.writeByte(value);
		}
	};

	StreamCodec<ByteStream, Short> UNSIGNED_BYTE = new StreamCodec<>() {
		public Short decode(final ByteStream buf) {
			return buf.readUnsignedByte();
		}

		public void encode(final ByteStream buf, final Short value) {
			buf.writeByte((byte) (value & 255));
		}
	};

	StreamCodec<ByteStream, Short> SHORT = new StreamCodec<>() {
		public Short decode(final ByteStream buf) {
			return buf.readShort();
		}

		public void encode(final ByteStream buf, final Short value) {
			buf.writeShort(value);
		}
	};

	StreamCodec<ByteStream, Integer> INT = new StreamCodec<>() {
		public Integer decode(final ByteStream buf) {
			return buf.readInt();
		}

		public void encode(final ByteStream buf, final Integer value) {
			buf.writeInt(value);
		}
	};

	StreamCodec<ByteStream, Long> LONG = new StreamCodec<>() {
		public Long decode(final ByteStream buf) {
			return buf.readLong();
		}

		public void encode(final ByteStream buf, final Long value) {
			buf.writeLong(value);
		}
	};

	StreamCodec<ByteStream, Float> FLOAT = new StreamCodec<>() {
		public Float decode(final ByteStream buf) {
			return buf.readFloat();
		}

		public void encode(final ByteStream buf, final Float value) {
			buf.writeFloat(value);
		}
	};

	StreamCodec<ByteStream, Double> DOUBLE = new StreamCodec<>() {
		public Double decode(final ByteStream buf) {
			return buf.readDouble();
		}

		public void encode(final ByteStream buf, final Double value) {
			buf.writeDouble(value);
		}
	};

	default StreamCodec<ByteStream, Double> doubleRange(final double min, final double max) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final Double value) {
				DOUBLE.encode(buf, Math.max(min, Math.min(max, value)));
			}

			@Override
			public Double decode(final ByteStream buf) {
				final double value = DOUBLE.decode(buf);
				if (value < min || value > max) {
					throw new RuntimeException("Double data is out of range!");
				} else {
					return value;
				}
			}
		};
	}

	static <T> StreamCodec<ByteStream, T[]> array(final StreamCodec<ByteStream, T> type, final int size, final Class<T> clazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final T[] values) {
				for (int i = 0; i < size; ++i) {
					type.encode(buf, values[i]);
				}
			}

			@Override
			public T[] decode(final ByteStream buf) {
				final T[] values = (T[]) Array.newInstance(clazz, size);
				for (int i = 0; i < size; ++i) {
					values[i] = type.decode(buf);
				}

				return values;
			}
		};
	}
}
