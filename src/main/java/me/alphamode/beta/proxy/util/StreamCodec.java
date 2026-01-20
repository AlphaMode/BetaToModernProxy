package me.alphamode.beta.proxy.util;

public interface StreamCodec<B, V> {
    void encode(B output, V value);

    V decode(B input);
}
