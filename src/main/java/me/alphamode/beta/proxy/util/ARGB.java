package me.alphamode.beta.proxy.util;

public interface ARGB {
	static int opaque(final int color) {
		return color | 0xFF000000;
	}

	static int transparent(final int color) {
		return color & 16777215;
	}
}
