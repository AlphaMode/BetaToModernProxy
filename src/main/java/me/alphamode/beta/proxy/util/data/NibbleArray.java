package me.alphamode.beta.proxy.util.data;

public record NibbleArray(byte[] data) {
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
