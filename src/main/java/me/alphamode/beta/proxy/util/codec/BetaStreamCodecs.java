package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.NettyByteStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface BetaStreamCodecs {
	int MAX_STRING_LENGTH = 32767;

	StreamCodec<ByteStream, byte[]> BYTE_ARRAY = new StreamCodec<>() {
		@Override
		public byte[] decode(final ByteStream stream) {
			final byte[] data = new byte[stream.readInt()];
			stream.readBytes(data);
			return data;
		}

		@Override
		public void encode(final ByteStream stream, final byte[] value) {
			stream.writeInt(value.length);
			stream.writeBytes(value);
		}
	};

	StreamCodec<ByteStream, byte[]> TINY_BYTE_ARRAY = new StreamCodec<>() {
		public byte[] decode(final ByteStream stream) {
			final byte[] data = new byte[stream.readByte() & 255];
			stream.readBytes(data);
			return data;
		}

		public void encode(final ByteStream stream, final byte[] data) {
			stream.writeByte((byte) data.length);
			stream.writeBytes(data);
		}
	};

	static StreamCodec<ByteStream, String> stringUtf8(final int maxLength) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream stream, final String msg) {
				if (msg.length() > maxLength) {
					throw new RuntimeException("Your mum too big");
				} else {
					final short len = (short) msg.length();
					stream.writeShort(len);
					for (int i = 0; i < len; ++i) {
						stream.writeChar(msg.charAt(i));
					}
				}
			}

			@Override
			public String decode(final ByteStream stream) {
				final int size = stream.readShort();
				if (size > maxLength) {
					throw new RuntimeException("Received string length longer than maximum allowed (" + size + " > " + maxLength + ")");
				} else if (size < 0) {
					throw new RuntimeException("Received string length is less than zero! Weird string!");
				} else {
					final StringBuilder builder = new StringBuilder();
					for (int i = 0; i < size; i++) {
						builder.append(stream.readChar());
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
			public void encode(final ByteStream stream, final String msg) {
				try {
					new DataOutputStream(new ByteBufOutputStream(NettyByteStream.unwrap(stream))).writeUTF(msg);
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}

			@Override
			public String decode(final ByteStream stream) {
				try {
					return new DataInputStream(new ByteBufInputStream(NettyByteStream.unwrap(stream))).readUTF();
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		};
	}

	static <T extends Enum<T>> StreamCodec<ByteStream, T> javaEnum(final Class<T> enumClazz) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream stream, final T value) {
				CommonStreamCodecs.BYTE.encode(stream, (byte) value.ordinal());
			}

			@Override
			public T decode(final ByteStream stream) {
				return enumClazz.getEnumConstants()[CommonStreamCodecs.BYTE.decode(stream)];
			}
		};
	}
}
