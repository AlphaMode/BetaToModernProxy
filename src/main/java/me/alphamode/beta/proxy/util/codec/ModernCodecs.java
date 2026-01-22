package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.util.UUID;
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
}
