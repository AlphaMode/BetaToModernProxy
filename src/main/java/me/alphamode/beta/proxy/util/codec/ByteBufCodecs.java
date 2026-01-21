package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface ByteBufCodecs {
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

	StreamCodec<ByteBuf, byte[]> TINY_BYTE_ARRAY = new StreamCodec<>() {
		public byte[] decode(final ByteBuf buf) {
			final byte[] data = new byte[buf.readByte() & 255];
			buf.readBytes(data);
			return data;
		}

		public void encode(final ByteBuf buf, final byte[] data) {
			buf.writeByte(data.length);
			buf.writeBytes(data);
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

	StreamCodec<ByteBuf, byte[]> BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(ByteBuf buf) {
			final byte[] data = new byte[buf.readInt()];
			buf.readBytes(data);
			return data;
		}

		@Override
		public void encode(ByteBuf buf, byte[] value) {
			buf.writeInt(value.length);
			buf.writeBytes(value);
		}
	};

	int MAX_STRING_LENGTH = 32767;

	static StreamCodec<ByteBuf, String> stringUtf8(final int maxLength) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final String msg) {
				if (msg.length() > maxLength) {
					throw new RuntimeException("Your mum too big");
				} else {
					final int len = msg.length();
					buf.writeShort(len);
					for (int i = 0; i < len; ++i) {
						buf.writeChar(msg.charAt(i));
					}
				}
			}

			@Override
			public String decode(final ByteBuf buf) {
				final int size = buf.readShort();
				if (size > maxLength) {
					throw new RuntimeException("Received string length longer than maximum allowed (" + size + " > " + maxLength + ")");
				} else if (size < 0) {
					throw new RuntimeException("Received string length is less than zero! Weird string!");
				} else {
					final StringBuilder builder = new StringBuilder();
					for (int i = 0; i < size; i++) {
						builder.append(buf.readChar());
					}

					return builder.toString();
				}
			}
		};
	}

	static StreamCodec<ByteBuf, String> stringUtf8() {
		return stringUtf8(MAX_STRING_LENGTH);
	}

	static StreamCodec<ByteBuf, String> stringJava() {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final String msg) {
				final DataOutputStream dos = new DataOutputStream(new ByteBufOutputStream(buf));
				try {
					dos.writeUTF(msg);
				} catch (Exception exception) {
					exception.printStackTrace();
				}
			}

			@Override
			public String decode(final ByteBuf buf) {
				final DataInputStream dis = new DataInputStream(new ByteBufInputStream(buf));
				try {
					return dis.readUTF();
				} catch (Exception exception) {
					exception.printStackTrace();
					return "";
				}
			}
		};
	}

	static <T> StreamCodec<ByteBuf, T[]> array(final StreamCodec<ByteBuf, T> type, final int size) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final T[] values) {
				for (int i = 0; i < size; ++i) {
					type.encode(buf, values[i]);
				}
			}

			@Override
			public T[] decode(final ByteBuf buf) {
				final T[] values = (T[]) new Object[size];
				for (int i = 0; i < size; ++i) {
					values[i] = type.decode(buf);
				}

				return values;
			}
		};
	}
}
