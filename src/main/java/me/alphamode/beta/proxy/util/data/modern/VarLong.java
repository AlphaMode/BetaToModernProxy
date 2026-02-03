package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;

public class VarLong {
	private static final int MAX_VARLONG_SIZE = 10;
	private static final int DATA_BITS_MASK = 127;
	private static final int CONTINUATION_BIT_MASK = 128;
	private static final int DATA_BITS_PER_BYTE = 7;

	public static int getByteSize(final long value) {
		for (int i = 1; i < MAX_VARLONG_SIZE; i++) {
			if ((value & -1L << i * DATA_BITS_PER_BYTE) == 0L) {
				return i;
			}
		}

		return 10;
	}

	public static boolean hasContinuationBit(final byte in) {
		return (in & 128) == 128;
	}

	public static long read(final ByteBuf buf) {
		long out = 0L;
		int bytes = 0;

		byte in;
		do {
			in = buf.readByte();
			out |= (long) (in & DATA_BITS_MASK) << bytes++ * DATA_BITS_PER_BYTE;
			if (bytes > MAX_VARLONG_SIZE) {
				throw new RuntimeException("VarLong too big");
			}
		} while (hasContinuationBit(in));

		return out;
	}

	public static ByteBuf write(final ByteBuf buf, long value) {
		while ((value & -CONTINUATION_BIT_MASK) != 0L) {
			buf.writeByte((int) (value & DATA_BITS_MASK) | CONTINUATION_BIT_MASK);
			value >>>= DATA_BITS_PER_BYTE;
		}

		buf.writeByte((int) value);
		return buf;
	}
}
