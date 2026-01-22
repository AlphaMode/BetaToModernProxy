package me.alphamode.beta.proxy.util.codec;

@FunctionalInterface
public interface StreamEncoder<O, T> {
	void encode(O buf, T value);
}
