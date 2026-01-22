package me.alphamode.beta.proxy.util.codec;

import io.netty.buffer.ByteBuf;

/// Helper class so we don't have to type SteamCodec<ByteBuf, ?> every time
public interface StreamBufCodec<T> extends StreamCodec<ByteBuf, T> {
}
