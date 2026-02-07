package me.alphamode.beta.proxy.util.data.modern.entity;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Direction;
import me.alphamode.beta.proxy.util.data.EntityDataSerializers;
import me.alphamode.beta.proxy.util.data.modern.item.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.level.block.BlockState;

public class ModernEntityData {

	public static final EntityDataSerializers SERIALIZERS = new EntityDataSerializers();
	public static final StreamCodec<ByteStream, Byte> BYTE = SERIALIZERS.register(0, CommonStreamCodecs.BYTE);
	public static final StreamCodec<ByteStream, Integer> INT = SERIALIZERS.register(1, ModernStreamCodecs.VAR_INT);
	public static final StreamCodec<ByteStream, Long> LONG = SERIALIZERS.register(2, ModernStreamCodecs.VAR_LONG);
	public static final StreamCodec<ByteStream, Float> FLOAT = SERIALIZERS.register(3, CommonStreamCodecs.FLOAT);
	public static final StreamCodec<ByteStream, String> STRING = SERIALIZERS.register(4, ModernStreamCodecs.STRING_UTF8);
	public static final StreamCodec<ByteStream, ModernItemStack> ITEM_STACK = SERIALIZERS.register(7, ModernItemStack.OPTIONAL_CODEC);
	public static final StreamCodec<ByteStream, BlockState> BLOCK_STATE = SERIALIZERS.register(14, BlockState.STREAM_CODEC);
	public static final StreamCodec<ByteStream, Direction> DIRECTION = SERIALIZERS.register(12, ModernStreamCodecs.VAR_INT.map(Direction::from3DDataValue, Direction::ordinal));
	public static final StreamCodec<ByteStream, Integer> PAINTING_VARIANT = SERIALIZERS.register(30, ModernStreamCodecs.VAR_INT);

	public static final StreamCodec<ByteStream, StreamCodec<ByteStream, ?>> ID_CODEC = ModernStreamCodecs.VAR_INT.map(SERIALIZERS::getSerializer, SERIALIZERS::getSerializedId);


	public static class Player {

	}
}
