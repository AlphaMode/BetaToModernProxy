package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.EncoderException;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import org.jspecify.annotations.Nullable;

// TODO Support modded entity data serializers
public class EntityDataSerializers {
	private final Int2ObjectMap<StreamCodec<ByteBuf, ?>> byId = new Int2ObjectLinkedOpenHashMap<>(16);
	private final Object2IntMap<StreamCodec<ByteBuf, ?>> byCodec = new Object2IntOpenHashMap<>();

	public <T> StreamCodec<ByteBuf, T> register(final int id, final StreamCodec<ByteBuf, T> serializer) {
		byId.put(id, serializer);
		byCodec.put(serializer, id);
		return serializer;
	}

	@Nullable
	public <T> StreamCodec<ByteBuf, T> getSerializer(final int id) {
		return (StreamCodec<ByteBuf, T>) byId.get(id);
	}

	public int getSerializedId(final StreamCodec<ByteBuf, ?> serializer) {
		final int serializerId = byCodec.getOrDefault(serializer, -1);
		if (serializerId == -1) {
			throw new EncoderException("Unknown serializer type " + serializer);
		} else {
			return serializerId;
		}
	}
}
