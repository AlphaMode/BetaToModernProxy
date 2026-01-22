package me.alphamode.beta.proxy.util.data.beta;

import me.alphamode.beta.proxy.util.data.Vec3i;

import java.util.Arrays;

public record BetaNibbleArray(byte[] data) {
	public void fill(int value) {
		Arrays.fill(this.data, (byte) value);
	}

	public void fill(int value, int offset, int length) {
		Arrays.fill(this.data, offset, offset + length, (byte) value);
	}

	public int get(int x, int y, int z) {
		final int packedPos = Vec3i.packNibble(x, y, z);
		final int dataIndex = packedPos >> 1;
		return (packedPos & 1) == 0
				? this.data[dataIndex] & 15
				: this.data[dataIndex] >> 4 & 15;
	}

	public void set(int x, int y, int z, int value) {
		final int packedPos = Vec3i.packNibble(x, y, z);
		final int dataIndex = packedPos >> 1;
		this.data[dataIndex] = (byte) (((packedPos & 1) == 0)
				? (this.data[dataIndex] & 240 | value & 15)
				: (this.data[dataIndex] & 15 | (value & 15) << 4));
	}

	public int size() {
		return this.isEmpty() ? 0 : this.data.length;
	}

	public boolean isEmpty() {
		return this.data == null;
	}
}
