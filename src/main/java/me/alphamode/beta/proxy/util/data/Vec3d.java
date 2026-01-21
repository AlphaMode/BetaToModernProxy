package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record Vec3d(double x, double y, double z) {
	public static final StreamCodec<ByteBuf, Vec3d> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Vec3d value) {
			buf.writeDouble(value.x);
			buf.writeDouble(value.y);
			buf.writeDouble(value.z);
		}

		@Override
		public Vec3d decode(final ByteBuf buf) {
			return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
		}
	};

	public Vec3i toVec3i() {
		return new Vec3i((int) this.x, (int) this.y, (int) this.z);
	}
}
