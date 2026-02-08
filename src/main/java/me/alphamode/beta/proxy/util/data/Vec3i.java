package me.alphamode.beta.proxy.util.data;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.BlockPos;

public record Vec3i(int x, int y, int z) {
	public static final StreamCodec<ByteStream, Vec3i> CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final Vec3i value) {
			stream.writeInt(value.x);
			stream.writeInt(value.y);
			stream.writeInt(value.z);
		}

		@Override
		public Vec3i decode(final ByteStream stream) {
			return new Vec3i(stream.readInt(), stream.readInt(), stream.readInt());
		}
	};

	public static final StreamCodec<ByteStream, Vec3i> TINY_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final Vec3i value) {
			stream.writeInt(value.x);
			stream.writeByte((byte) value.y);
			stream.writeInt(value.z);
		}

		@Override
		public Vec3i decode(final ByteStream stream) {
			return new Vec3i(stream.readInt(), stream.readUnsignedByte(), stream.readInt());
		}
	};

	public static final StreamCodec<ByteStream, Vec3i> SEMI_TINY_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteStream stream, final Vec3i value) {
			stream.writeInt(value.x);
			stream.writeShort((short) value.y);
			stream.writeInt(value.z);
		}

		@Override
		public Vec3i decode(final ByteStream stream) {
			return new Vec3i(stream.readInt(), stream.readShort(), stream.readInt());
		}
	};

	public static StreamCodec<ByteStream, Vec3i> relative(final Vec3i origin) {
		return new StreamCodec<>() {
			@Override
			public void encode(final ByteStream stream, final Vec3i value) {
				stream.writeByte((byte) (value.x - origin.x));
				stream.writeByte((byte) (value.y - origin.y));
				stream.writeByte((byte) (value.z - origin.z));
			}

			@Override
			public Vec3i decode(final ByteStream stream) {
				return new Vec3i(stream.readByte() + origin.x, stream.readByte() + origin.y, stream.readByte() + origin.z);
			}
		};
	}

	public static Vec3i ofX(final int x) {
		return new Vec3i(x, 0, 0);
	}

	public static Vec3i ofY(final int y) {
		return new Vec3i(0, y, 0);
	}

	public static Vec3i ofZ(final int z) {
		return new Vec3i(0, 0, z);
	}

	public BlockPos toBlockPos() {
		return new BlockPos(this.x, this.y, this.z);
	}

	public Vec3i divide(final int divider) {
		return new Vec3i(this.x / divider, this.y / divider, this.z / divider);
	}

	public Vec3d toVec3d() {
		return new Vec3d(this.x, this.y, this.z);
	}
}
