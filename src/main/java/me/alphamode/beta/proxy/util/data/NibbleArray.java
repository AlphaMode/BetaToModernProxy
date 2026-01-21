package me.alphamode.beta.proxy.util.data;

import java.util.Arrays;

public record NibbleArray(byte[] data) {
	public void fill(int value) {
		Arrays.fill(this.data, (byte) value);
	}

	public void fill(int value, int offset, int length) {
		Arrays.fill(this.data, offset, offset + length, (byte) value);
	}

	public byte get(int x, int y, int z) {
		return 0; // TODO
	}

	public void set(int x, int y, int z, int value) {
		// TODO
	}

	public int size() {
		return this.isEmpty() ? 0 : this.data.length;
	}

	public boolean isEmpty() {
		return this.data == null;
	}
}
