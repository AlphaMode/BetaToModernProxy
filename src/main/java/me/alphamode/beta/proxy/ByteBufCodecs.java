package me.alphamode.beta.proxy;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.PacketUtils;

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

    static StreamCodec<ByteBuf, String> stringUtf8(final int maxStringLength) {
        return new StreamCodec<>() {
            public String decode(final ByteBuf input) {
                return PacketUtils.readString(input, maxStringLength);
            }

            public void encode(final ByteBuf output, final String value) {
                PacketUtils.writeString(value, output);
            }
        };
    }
}
