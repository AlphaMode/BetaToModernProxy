package me.alphamode.beta.proxy.util;

public final class ARGB {
    private ARGB() {
    }

    public static int opaque(final int color) {
        return color | 0xFF000000;
    }

    public static int transparent(final int color) {
        return color & 16777215;
    }
}
