package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.util.NibbleArray;

public record BetaNibbleArray(byte[] data) implements NibbleArray {
	public BetaNibbleArray(int size) {
		this(new byte[size >> 1]);
	}

    @Override
	public int get(final int index) {
		final int dataIndex = index >> 1;
		return (index & 1) == 0
				? this.data[dataIndex] & 15
				: this.data[dataIndex] >> 4 & 15;
	}

    @Override
	public void set(final int index, final int value) {
		final int dataIndex = index >> 1;
		this.data[dataIndex] = (byte) (((index & 1) == 0)
				? (this.data[dataIndex] & 240 | value & 15)
				: (this.data[dataIndex] & 15 | (value & 15) << 4));
	}

    @Override
    public int getIndex(final int x, final int y, final int z) {
        return packIndex(x, y, z);
    }

    public static int packIndex(final int x, final int y, final int z) {
        return x << 11 | z << 7 | y;
    }

    @Override
	public int size() {
		return this.isEmpty() ? 0 : this.data.length;
	}

    @Override
	public boolean isEmpty() {
		return this.data == null;
	}
}
