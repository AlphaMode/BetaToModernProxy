package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public interface BetaCodecs {
	int MAX_STRING_LENGTH = 32767;

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
					throw new RuntimeException(exception);
				}
			}

			@Override
			public String decode(final ByteBuf buf) {
				final DataInputStream dis = new DataInputStream(new ByteBufInputStream(buf));
				try {
					return dis.readUTF();
				} catch (Exception exception) {
					throw new RuntimeException(exception);
				}
			}
		};
	}
}
