package me.alphamode.beta.proxy.util.codec;

@FunctionalInterface
public interface StreamMemberEncoder<O, T> {
    void encode(T value, O output);
}
