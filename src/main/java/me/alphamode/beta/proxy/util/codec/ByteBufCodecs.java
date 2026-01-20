package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;

public interface ByteBufCodecs {
    StreamCodec<ByteBuf, Boolean> BOOL = new StreamCodec<>() {
        public Boolean decode(final ByteBuf input) {
            return input.readBoolean();
        }

        public void encode(final ByteBuf output, final Boolean value) {
            output.writeBoolean(value);
        }
    };

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

	StreamCodec<ByteBuf, Long> LONG = new StreamCodec<>() {
		public Long decode(final ByteBuf input) {
			return input.readLong();
		}

		public void encode(final ByteBuf output, final Long value) {
			output.writeLong(value);
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

    StreamCodec<ByteBuf, byte[]> BYTE_ARRAY = new StreamCodec<>() {
        @Override
        public byte[] decode(ByteBuf input) {
            int size = input.readInt();
            byte[] bytes = new byte[size];
            input.readBytes(bytes);
            return bytes;
        }

        @Override
        public void encode(ByteBuf output, byte[] value) {
            output.writeInt(value.length);
            output.writeBytes(value);
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
}
