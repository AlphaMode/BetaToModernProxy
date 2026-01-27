package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.Mth;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockPos(int x, int y, int z) {
	public static final StreamCodec<ByteBuf, BlockPos> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			BlockPos::pack,
			BlockPos::unpack
	);

	public static final int PACKED_HORIZONTAL_LENGTH = 1 + Mth.log2(Mth.smallestEncompassingPowerOfTwo(30000000));
	public static final int PACKED_Y_LENGTH = 64 - 2 * PACKED_HORIZONTAL_LENGTH;
	private static final long PACKED_X_MASK = (1L << PACKED_HORIZONTAL_LENGTH) - 1L;
	private static final long PACKED_Y_MASK = (1L << PACKED_Y_LENGTH) - 1L;
	private static final long PACKED_Z_MASK = (1L << PACKED_HORIZONTAL_LENGTH) - 1L;
	private static final int Y_OFFSET = 0;
	private static final int Z_OFFSET = PACKED_Y_LENGTH;
	private static final int X_OFFSET = PACKED_Y_LENGTH + PACKED_HORIZONTAL_LENGTH;

	public static int getX(final long pos) {
		return (int) (pos << 64 - X_OFFSET - PACKED_HORIZONTAL_LENGTH >> 64 - PACKED_HORIZONTAL_LENGTH);
	}

	public static int getY(final long pos) {
		return (int) (pos << 64 - PACKED_Y_LENGTH >> 64 - PACKED_Y_LENGTH);
	}

	public static int getZ(final long pos) {
		return (int) (pos << 64 - Z_OFFSET - PACKED_HORIZONTAL_LENGTH >> 64 - PACKED_HORIZONTAL_LENGTH);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public int getZ() {
		return this.z;
	}

	public static BlockPos unpack(final long pos) {
		return new BlockPos(getX(pos), getY(pos), getZ(pos));
	}

	public static long pack(final int x, final int y, final int z) {
		long node = 0L;
		node |= (x & PACKED_X_MASK) << X_OFFSET;
		node |= (y & PACKED_Y_MASK) << Y_OFFSET;
		return node | (z & PACKED_Z_MASK) << Z_OFFSET;
	}

	public long pack() {
		return pack(this.x, this.y, this.z);
	}
}
