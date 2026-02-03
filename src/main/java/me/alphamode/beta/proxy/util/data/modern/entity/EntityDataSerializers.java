package me.alphamode.beta.proxy.util.data.modern.entity;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Direction;
import me.alphamode.beta.proxy.util.data.modern.item.ModernItemStack;
import org.jspecify.annotations.Nullable;

// TODO Support modded entity data serializers
public class EntityDataSerializers {
	private static final Int2ObjectMap<StreamCodec<ByteBuf, ?>> BY_ID = new Int2ObjectLinkedOpenHashMap<>(16);
	private static final Object2IntMap<StreamCodec<ByteBuf, ?>> BY_CODEC = new Object2IntOpenHashMap<>();

	public static <T> StreamCodec<ByteBuf, T> register(final int id, final StreamCodec<ByteBuf, T> serializer) {
		BY_ID.put(id, serializer);
		BY_CODEC.put(serializer, id);
		return serializer;
	}

	public static final StreamCodec<ByteBuf, Byte> BYTE = register(0, BasicStreamCodecs.BYTE);
	public static final StreamCodec<ByteBuf, Integer> INT = register(1, ModernStreamCodecs.VAR_INT);
	public static final StreamCodec<ByteBuf, Long> LONG = register(2, ModernStreamCodecs.VAR_LONG);
	public static final StreamCodec<ByteBuf, Float> FLOAT = register(3, BasicStreamCodecs.FLOAT);
	public static final StreamCodec<ByteBuf, String> STRING = register(4, ModernStreamCodecs.STRING_UTF8);
	public static final StreamCodec<ByteBuf, ModernItemStack> ITEM_STACK = register(7, ModernItemStack.OPTIONAL_CODEC);
	//    public static final StreamCodec<ByteBuf, BlockState> BLOCK_STATE = register(14, ModernStreamCodecs.idMapper(BlockStateRegistry.BLOCK_STATE_REGISTRY));
	public static final StreamCodec<ByteBuf, Direction> DIRECTION = register(12, ModernStreamCodecs.VAR_INT.map(Direction::from3DDataValue, Direction::ordinal));
	public static final StreamCodec<ByteBuf, Integer> PAINTING_VARIANT = register(30, ModernStreamCodecs.VAR_INT);

	public static final StreamCodec<ByteBuf, StreamCodec<ByteBuf, ?>> ID_CODEC = ModernStreamCodecs.VAR_INT.map(EntityDataSerializers::getSerializer, EntityDataSerializers::getSerializedId);

	@Nullable
	public static StreamCodec<ByteBuf, ?> getSerializer(final int id) {
		return BY_ID.get(id);
	}

	public static int getSerializedId(final StreamCodec<ByteBuf, ?> serializer) {
		int serializerId = BY_CODEC.getOrDefault(serializer, -1);
		if (serializerId == -1) {
			throw new EncoderException("Unknown serializer type " + serializer);
		}
		return serializerId;
	}
}
