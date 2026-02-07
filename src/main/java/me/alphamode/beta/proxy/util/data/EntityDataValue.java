package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;

public interface EntityDataValue<T> {
    int id();

    T data();

    void write(final ByteBuf buf);
}
