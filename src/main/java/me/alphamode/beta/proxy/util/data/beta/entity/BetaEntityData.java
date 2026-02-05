package me.alphamode.beta.proxy.util.data.beta.entity;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.EntityDataSerializers;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public class BetaEntityData {
    public static final EntityDataSerializers SERIALIZERS = new EntityDataSerializers();

    public static final StreamCodec<ByteBuf, Byte> BYTE = SERIALIZERS.register(0, BasicStreamCodecs.BYTE);
    public static final StreamCodec<ByteBuf, Short> SHORT = SERIALIZERS.register(1, BasicStreamCodecs.SHORT);
    public static final StreamCodec<ByteBuf, Integer> INT = SERIALIZERS.register(2, BasicStreamCodecs.INT);
    public static final StreamCodec<ByteBuf, Float> FLOAT = SERIALIZERS.register(3, BasicStreamCodecs.FLOAT);
    public static final StreamCodec<ByteBuf, String> STRING = SERIALIZERS.register(4, BetaStreamCodecs.stringUtf8());
    public static final StreamCodec<ByteBuf, BetaItemStack> ITEM_STACK = SERIALIZERS.register(5, BetaItemStack.CODEC);
    public static final StreamCodec<ByteBuf, Vec3i> VEC3I = SERIALIZERS.register(6, Vec3i.CODEC);
}
