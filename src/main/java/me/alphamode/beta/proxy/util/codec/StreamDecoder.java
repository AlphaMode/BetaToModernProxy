package me.alphamode.beta.proxy.util.codec;

@FunctionalInterface
public interface StreamDecoder<I, T> {
    T decode(I input);
}
