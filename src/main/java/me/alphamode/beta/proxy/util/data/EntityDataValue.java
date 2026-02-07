package me.alphamode.beta.proxy.util.data;


import me.alphamode.beta.proxy.util.ByteStream;

public interface EntityDataValue<T> {
	int id();

	T data();

	void write(final ByteStream buf);
}
