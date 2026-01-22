package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;
import net.raphimc.netminecraft.packet.PacketTypes;

public interface ModernCodecs {
    short MAX_STRING_LENGTH = 32767;

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

    static StreamCodec<ByteBuf, String> stringUtf8(final int maxLength) {
        return new StreamCodec<>() {
            @Override
            public void encode(final ByteBuf output, final String value) {
                PacketTypes.writeString(output, value);
            }

            @Override
            public String decode(final ByteBuf input) {
                return PacketTypes.readString(input, maxLength);
            }
        };
    }

    static StreamCodec<ByteBuf, String> stringUtf8() {
        return stringUtf8(MAX_STRING_LENGTH);
    }
}
