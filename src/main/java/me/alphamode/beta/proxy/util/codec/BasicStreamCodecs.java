package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Array;

public interface BasicStreamCodecs {
	StreamCodec<ByteBuf, Boolean> BOOL = new StreamCodec<>() {
		public Boolean decode(final ByteBuf buf) {
			return buf.readBoolean();
		}

		public void encode(final ByteBuf buf, final Boolean value) {
			buf.writeBoolean(value);
		}
	};

	StreamCodec<ByteBuf, Byte> BYTE = new StreamCodec<>() {
		public Byte decode(final ByteBuf buf) {
			return buf.readByte();
		}

		public void encode(final ByteBuf buf, final Byte value) {
			buf.writeByte(value);
		}
	};

	StreamCodec<ByteBuf, Short> UNSIGNED_BYTE = new StreamCodec<>() {
		public Short decode(final ByteBuf buf) {
			return buf.readUnsignedByte();
		}

		public void encode(final ByteBuf buf, final Short value) {
			buf.writeByte(value);
		}
	};

	StreamCodec<ByteBuf, Short> SHORT = new StreamCodec<>() {
		public Short decode(final ByteBuf buf) {
			return buf.readShort();
		}

		public void encode(final ByteBuf buf, final Short value) {
			buf.writeShort(value);
		}
	};

	StreamCodec<ByteBuf, Integer> INT = new StreamCodec<>() {
		public Integer decode(final ByteBuf buf) {
			return buf.readInt();
		}

		public void encode(final ByteBuf buf, final Integer value) {
			buf.writeInt(value);
		}
	};

	StreamCodec<ByteBuf, Long> LONG = new StreamCodec<>() {
		public Long decode(final ByteBuf buf) {
			return buf.readLong();
		}

		public void encode(final ByteBuf buf, final Long value) {
			buf.writeLong(value);
		}
	};

	StreamCodec<ByteBuf, Float> FLOAT = new StreamCodec<>() {
		public Float decode(final ByteBuf buf) {
			return buf.readFloat();
		}

		public void encode(final ByteBuf buf, final Float value) {
			buf.writeFloat(value);
		}
	};

	StreamCodec<ByteBuf, Double> DOUBLE = new StreamCodec<>() {
		public Double decode(final ByteBuf buf) {
			return buf.readDouble();
		}

		public void encode(final ByteBuf buf, final Double value) {
			buf.writeDouble(value);
		}
	};

	default StreamCodec<ByteBuf, Double> doubleRange(final double min, final double max) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final Double value) {
				DOUBLE.encode(buf, Math.max(min, Math.min(max, value)));
			}

			@Override
			public Double decode(final ByteBuf buf) {
				final double value = DOUBLE.decode(buf);
				if (value < min || value > max) {
					throw new RuntimeException("Double data is out of range!");
				} else {
					return value;
				}
			}
		};
	}

	static <T> StreamCodec<ByteBuf, T[]> array(final StreamCodec<ByteBuf, T> type, final int size, final Class<T> clazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final T[] values) {
				for (int i = 0; i < size; ++i) {
					type.encode(buf, values[i]);
				}
			}

			@Override
			public T[] decode(final ByteBuf buf) {
				final T[] values = (T[]) Array.newInstance(clazz, size);
				for (int i = 0; i < size; ++i) {
					values[i] = type.decode(buf);
				}

				return values;
			}
		};
	}
}
