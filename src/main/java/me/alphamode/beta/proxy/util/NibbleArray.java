package me.alphamode.beta.proxy.util;

public interface NibbleArray {
	int LAYER_COUNT = 16;
	int LAYER_SIZE = 128;
	int NIBBLE_SIZE = 4;

	default int get(final int x, final int y, final int z) {
		return get(getIndex(x, y, z));
	}

	default void set(final int x, final int y, final int z, final int value) {
		set(getIndex(x, y, z), value);
	}

	int get(final int index);

	void set(int index, int value);

	int getIndex(int x, int y, int z);

	int size();

	boolean isEmpty();
}
