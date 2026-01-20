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

    public static StreamCodec<ByteBuf, Vec3i> relative(Vec3i origin) {
        return new StreamCodec<>() {
            @Override
            public void encode(ByteBuf output, Vec3i value) {
                output.writeByte(value.x - origin.x);
                output.writeByte(value.y - origin.y);
                output.writeByte(value.z - origin.z);
            }

            @Override
            public Vec3i decode(ByteBuf input) {
                int rX = input.readByte();
                int rY = input.readByte();
                int rZ = input.readByte();
                return new Vec3i(rX + origin.x, rY + origin.y, rZ + origin.z);
            }
        };
    }
}
