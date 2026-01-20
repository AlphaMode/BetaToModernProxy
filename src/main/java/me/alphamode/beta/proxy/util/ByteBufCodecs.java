package me.alphamode.beta.proxy.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public interface ByteBufCodecs {
	StreamCodec<ByteBuf, Byte> BYTE = new StreamCodec<>() {
		public Byte decode(final ByteBuf input) {
			return input.readByte();
		}

		public void encode(final ByteBuf output, final Byte value) {
			output.writeByte(value);
		}
	};

	StreamCodec<ByteBuf, Short> SHORT = new StreamCodec<>() {
		public Short decode(final ByteBuf input) {
			return input.readShort();
		}

		public void encode(final ByteBuf output, final Short value) {
			output.writeShort(value);
		}
	};

	StreamCodec<ByteBuf, Integer> INT = new StreamCodec<>() {
		public Integer decode(final ByteBuf input) {
			return input.readInt();
		}

		public void encode(final ByteBuf output, final Integer value) {
			output.writeInt(value);
		}
	};

	StreamCodec<ByteBuf, Float> FLOAT = new StreamCodec<>() {
		public Float decode(final ByteBuf input) {
			return input.readFloat();
		}

		public void encode(final ByteBuf output, final Float value) {
			output.writeFloat(value);
		}
	};

	int MAX_STRING_LENGTH = 32767;

	static StreamCodec<ByteBuf, String> stringUtf8(final int maxLength) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final String msg) {
				if (msg.length() > maxLength) {
					throw new RuntimeException("String too big");
				} else {
					buf.writeShort(msg.length());
					buf.writeCharSequence(msg, StandardCharsets.UTF_16BE);
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
					return buf.readCharSequence(size, StandardCharsets.UTF_16BE).toString();
				}
			}
		};
	}

	static StreamCodec<ByteBuf, String> stringUtf8() {
		return stringUtf8(MAX_STRING_LENGTH);
	}

}
