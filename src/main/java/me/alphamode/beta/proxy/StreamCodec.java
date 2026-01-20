package me.alphamode.beta.proxy;

public interface StreamCodec<B, V> {
    void encode(B output, V value);

    V decode(B input);
}
