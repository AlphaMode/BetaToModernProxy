package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.NibbleArray;

public record ModernNibbleArray(byte[] data, int defaultValue) implements NibbleArray {
    public static final int SIZE = 2048;

    public ModernNibbleArray(final byte[] data) {
        this(data, 0);
    }

    public ModernNibbleArray {
        if (data.length != SIZE) {
            throw new IllegalArgumentException("ModernNibbleArray should be 2048 bytes not: " + data.length);
        }
    }

    @Override
    public int get(int index) {
        if (this.data == null) {
            return this.defaultValue;
        } else {
            int position = getByteIndex(index);
            int nibble = getNibbleIndex(index);
            return this.data[position] >> NIBBLE_SIZE * nibble & 15;
        }
    }

    @Override
    public void set(int index, int value) {
        int position = getByteIndex(index);
        int nibble = getNibbleIndex(index);
        int mask = ~(15 << NIBBLE_SIZE * nibble);
        int valueToSet = (value & 15) << NIBBLE_SIZE * nibble;
        data[position] = (byte)(data[position] & mask | valueToSet);
    }

    private static int getNibbleIndex(final int index) {
        return index & 1;
    }

    private static int getByteIndex(final int position) {
        return position >> 1;
    }

    @Override
    public int getIndex(final int x, final int y, final int z) {
        return packIndex(x, y, z);
    }

    private static int packIndex(final int x, final int y, final int z) {
        return y << 8 | z << 4 | x;
    }

    @Override
    public int size() {
        return data.length;
    }

    @Override
    public boolean isEmpty() {
        return this.data == null && this.defaultValue == 0;
    }
}
