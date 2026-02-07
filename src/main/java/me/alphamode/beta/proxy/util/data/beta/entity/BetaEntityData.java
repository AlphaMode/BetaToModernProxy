package me.alphamode.beta.proxy.util.data.beta.entity;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.EntityDataSerializers;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public class BetaEntityData {
	public static final EntityDataSerializers SERIALIZERS = new EntityDataSerializers();

	public static final StreamCodec<ByteStream, Byte> BYTE = SERIALIZERS.register(0, CommonStreamCodecs.BYTE);
	public static final StreamCodec<ByteStream, Short> SHORT = SERIALIZERS.register(1, CommonStreamCodecs.SHORT);
	public static final StreamCodec<ByteStream, Integer> INT = SERIALIZERS.register(2, CommonStreamCodecs.INT);
	public static final StreamCodec<ByteStream, Float> FLOAT = SERIALIZERS.register(3, CommonStreamCodecs.FLOAT);
	public static final StreamCodec<ByteStream, String> STRING = SERIALIZERS.register(4, BetaStreamCodecs.stringUtf8());
	public static final StreamCodec<ByteStream, BetaItemStack> ITEM_STACK = SERIALIZERS.register(5, BetaItemStack.CODEC);
	public static final StreamCodec<ByteStream, Vec3i> VEC3I = SERIALIZERS.register(6, Vec3i.CODEC);
}
