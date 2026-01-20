package me.alphamode.beta.proxy.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.StreamCodec;

public record BetaItemStack(int id, int count, int auxValue) {
    public static final StreamCodec<ByteBuf, BetaItemStack> CODEC = new StreamCodec<>() {
        @Override
        public void encode(ByteBuf output, BetaItemStack value) {
            output.writeShort(value.id);
            output.writeByte(value.count);
            output.writeShort(value.auxValue);
        }

        @Override
        public BetaItemStack decode(ByteBuf input) {
            return new BetaItemStack(input.readShort(), input.readByte(), input.readShort());
        }
    };

    public static final StreamCodec<ByteBuf, BetaItemStack> OPTIONAL_CODEC = new StreamCodec<>() {
        @Override
        public void encode(ByteBuf output, BetaItemStack value) {
            if (value == null) {
                output.writeShort(-1);
            } else {
                output.writeShort(value.id);
                output.writeByte(value.count);
                output.writeShort(value.auxValue);
            }
        }

        @Override
        public BetaItemStack decode(ByteBuf input) {
            int id = input.readShort();
            if (id >= 0) {
                int count = input.readByte();
                int auxValue = input.readShort();
                return new BetaItemStack(id, count, auxValue);
            } else {
                return null;
            }
        }
    };
}
