package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record Vec3d(double x, double y, double z) {
	public static final Vec3d ZERO = new Vec3d(0, 0, 0);
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

	public static final StreamCodec<ByteBuf, Vec3d> LERP_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final Vec3d value) {
			double chessboardLength = Mth.absMax(value.x, Mth.absMax(value.y, value.z));
			if (chessboardLength < 3.051944088384301E-5) {
				buf.writeByte(0);
			} else {
				final long scale = Mth.ceilLong(chessboardLength);
				final boolean isPartial = (scale & 3L) != scale;
				final long markers = isPartial ? scale & 3L | 4L : scale;
				final long xn = pack(value.x / scale) << 3;
				final long yn = pack(value.y / scale) << 18;
				final long zn = pack(value.z / scale) << 33;
				final long buffer = markers | xn | yn | zn;
				buf.writeByte((byte) buffer);
				buf.writeByte((byte) (buffer >> 8));
				buf.writeInt((int) (buffer >> 16));
				if (isPartial) {
					ModernStreamCodecs.VAR_INT.encode(buf, (int) (scale >> 2));
				}
			}
		}

		@Override
		public Vec3d decode(final ByteBuf buf) {
			final int lowest = buf.readByte();
			if (lowest == 0) {
				return Vec3d.ZERO;
			} else {
				final int middle = buf.readUnsignedByte();
				final long highest = buf.readUnsignedInt();
				final long buffer = highest << 16 | middle << 8 | lowest;

				long scale = lowest & 3;
				if ((lowest & 4) == 4) {
					scale |= (ModernStreamCodecs.VAR_INT.decode(buf) & 4294967295L) << 2;
				}

				return new Vec3d(unpack(buffer >> 3) * scale, unpack(buffer >> 18) * scale, unpack(buffer >> 33) * scale);
			}
		}
	};

	public Vec3d copy() {
		return new Vec3d(this.x, this.y, this.z);
	}

	public Vec3i toVec3i() {
		return new Vec3i((int) this.x, (int) this.y, (int) this.z);
	}

	private static long pack(double value) {
		return Math.round((value * 0.5 + 0.5) * 32766.0);
	}

	private static double unpack(long value) {
		return Math.min((double) (value & 32767L), 32766.0) * 2.0 / 32766.0 - 1.0;
	}
}
