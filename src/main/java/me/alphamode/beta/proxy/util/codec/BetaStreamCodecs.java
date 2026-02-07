package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import me.alphamode.beta.proxy.util.ByteStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface BetaStreamCodecs {
	int MAX_STRING_LENGTH = 32767;

	StreamCodec<ByteStream, byte[]> BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(final ByteStream buf) {
			final byte[] data = new byte[buf.readInt()];
			buf.readBytes(data);
			return data;
		}

		@Override
		public void encode(final ByteStream buf, final byte[] value) {
			buf.writeInt(value.length);
			buf.writeBytes(value);
		}
	};

	StreamCodec<ByteStream, byte[]> TINY_BYTE_ARRAY = new StreamCodec<>() {
		public byte[] decode(final ByteStream buf) {
			final byte[] data = new byte[buf.readByte() & 255];
			buf.readBytes(data);
			return data;
		}

		public void encode(final ByteStream buf, final byte[] data) {
			buf.writeByte((byte) data.length);
			buf.writeBytes(data);
		}
	};

	static StreamCodec<ByteStream, String> stringUtf8(final int maxLength) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final String msg) {
				if (msg.length() > maxLength) {
					throw new RuntimeException("Your mum too big");
				} else {
					final short len = (short) msg.length();
					buf.writeShort(len);
					for (int i = 0; i < len; ++i) {
						buf.writeChar(msg.charAt(i));
					}
				}
			}

			@Override
			public String decode(final ByteStream buf) {
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

	static StreamCodec<ByteStream, String> stringUtf8() {
		return stringUtf8(MAX_STRING_LENGTH);
	}

	static StreamCodec<ByteStream, String> stringJava() {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final String msg) {
				final DataOutputStream dos = new DataOutputStream(new ByteBufOutputStream((ByteBuf) buf));
				try {
					dos.writeUTF(msg);
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			@Override
			public String decode(final ByteStream buf) {
				final DataInputStream dis = new DataInputStream(new ByteBufInputStream((ByteBuf) buf));
				try {
					return dis.readUTF();
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		};
	}

	static <T extends Enum<T>> StreamCodec<ByteStream, T> javaEnum(final Class<T> enumClazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream buf, final T value) {
				CommonStreamCodecs.BYTE.encode(buf, (byte) value.ordinal());
			}

			@Override
			public T decode(final ByteStream buf) {
				return enumClazz.getEnumConstants()[CommonStreamCodecs.BYTE.decode(buf)];
			}
		};
	}
}
