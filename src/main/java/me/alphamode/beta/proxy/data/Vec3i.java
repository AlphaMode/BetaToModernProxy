package me.alphamode.beta.proxy.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.StreamCodec;

public record Vec3i(int x, int y, int z) {
    public static final StreamCodec<ByteBuf, Vec3i> CODEC = new StreamCodec<>() {
        @Override
        public void encode(ByteBuf output, Vec3i value) {
            output.writeInt(value.x);
            output.writeInt(value.y);
            output.writeInt(value.z);
        }

        @Override
        public Vec3i decode(ByteBuf input) {
            return new Vec3i(input.readInt(), input.readInt(), input.readInt());
        }
    };
}
