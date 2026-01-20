package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record Vec3i(int x, int y, int z) {
	public static final StreamCodec<ByteBuf, Vec3i> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Vec3i value) {
			buf.writeInt(value.x);
			buf.writeInt(value.y);
			buf.writeInt(value.z);
		}

		@Override
		public Vec3i decode(final ByteBuf buf) {
			return new Vec3i(buf.readInt(), buf.readInt(), buf.readInt());
		}
	};

	public static StreamCodec<ByteBuf, Vec3i> relative(final Vec3i origin) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteBuf buf, final Vec3i value) {
				buf.writeByte(value.x - origin.x);
				buf.writeByte(value.y - origin.y);
				buf.writeByte(value.z - origin.z);
			}

			@Override
			public Vec3i decode(final ByteBuf buf) {
				return new Vec3i(buf.readByte() + origin.x, buf.readByte() + origin.y, buf.readByte() + origin.z);
			}
		};
	}
}
