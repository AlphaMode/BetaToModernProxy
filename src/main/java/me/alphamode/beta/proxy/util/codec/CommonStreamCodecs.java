package me.alphamode.beta.proxy.util.codec;

import me.alphamode.beta.proxy.util.ByteStream;

import java.lang.reflect.Array;

public interface CommonStreamCodecs {
	StreamCodec<ByteStream, Boolean> BOOL = new StreamCodec<>() {
		public Boolean decode(final ByteStream stream) {
			return stream.readBoolean();
		}

		public void encode(final ByteStream stream, final Boolean value) {
			stream.writeBoolean(value);
		}
	};

	StreamCodec<ByteStream, Byte> BYTE = new StreamCodec<>() {
		public Byte decode(final ByteStream stream) {
			return stream.readByte();
		}

		public void encode(final ByteStream stream, final Byte value) {
			stream.writeByte(value);
		}
	};

	StreamCodec<ByteStream, Short> UNSIGNED_BYTE = new StreamCodec<>() {
		public Short decode(final ByteStream stream) {
			return stream.readUnsignedByte();
		}

		public void encode(final ByteStream stream, final Short value) {
			stream.writeByte((byte) (value & 255));
		}
	};

	StreamCodec<ByteStream, Short> SHORT = new StreamCodec<>() {
		public Short decode(final ByteStream stream) {
			return stream.readShort();
		}

		public void encode(final ByteStream stream, final Short value) {
			stream.writeShort(value);
		}
	};

	StreamCodec<ByteStream, Integer> INT = new StreamCodec<>() {
		public Integer decode(final ByteStream stream) {
			return stream.readInt();
		}

		public void encode(final ByteStream stream, final Integer value) {
			stream.writeInt(value);
		}
	};

	StreamCodec<ByteStream, Long> LONG = new StreamCodec<>() {
		public Long decode(final ByteStream stream) {
			return stream.readLong();
		}

		public void encode(final ByteStream stream, final Long value) {
			stream.writeLong(value);
		}
	};

	StreamCodec<ByteStream, Float> FLOAT = new StreamCodec<>() {
		public Float decode(final ByteStream stream) {
			return stream.readFloat();
		}

		public void encode(final ByteStream stream, final Float value) {
			stream.writeFloat(value);
		}
	};

	StreamCodec<ByteStream, Double> DOUBLE = new StreamCodec<>() {
		public Double decode(final ByteStream stream) {
			return stream.readDouble();
		}

		public void encode(final ByteStream stream, final Double value) {
			stream.writeDouble(value);
		}
	};

	default StreamCodec<ByteStream, Double> doubleRange(final double min, final double max) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream stream, final Double value) {
				DOUBLE.encode(stream, Math.max(min, Math.min(max, value)));
			}

			@Override
			public Double decode(final ByteStream stream) {
				final double value = DOUBLE.decode(stream);
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
			public void encode(final ByteStream stream, final T[] values) {
				for (int i = 0; i < size; ++i) {
					type.encode(stream, values[i]);
				}
			}

			@Override
			public T[] decode(final ByteStream stream) {
				final T[] values = (T[]) Array.newInstance(clazz, size);
				for (int i = 0; i < size; ++i) {
					values[i] = type.decode(stream);
				}

				return values;
			}
		};
	}
}
