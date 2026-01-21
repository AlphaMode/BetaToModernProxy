package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record Vec3d(double x, double y, double z) {
    public static final StreamCodec<ByteBuf, Vec3d> CODEC = new StreamCodec<>() {
        @Override
        public void encode(ByteBuf output, Vec3d value) {
            output.writeDouble(value.x);
            output.writeDouble(value.y);
            output.writeDouble(value.z);
        }

        @Override
        public Vec3d decode(ByteBuf input) {
            return new Vec3d(input.readDouble(), input.readDouble(), input.readDouble());
        }
    };

    public Vec3i toVec3i() {
        return new Vec3i((int) this.x, (int) this.y, (int) this.z);
    }
}
