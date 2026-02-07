package me.alphamode.beta.proxy.util.data;

import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import org.jspecify.annotations.Nullable;

// TODO Support modded entity data serializers
public class EntityDataSerializers {
	private final Int2ObjectMap<StreamCodec<ByteStream, ?>> byId = new Int2ObjectLinkedOpenHashMap<>(16);
	private final Object2IntMap<StreamCodec<ByteStream, ?>> byCodec = new Object2IntOpenHashMap<>();

	public <T> StreamCodec<ByteStream, T> register(final int id, final StreamCodec<ByteStream, T> serializer) {
		byId.put(id, serializer);
		byCodec.put(serializer, id);
		return serializer;
	}

	@Nullable
	public <T> StreamCodec<ByteStream, T> getSerializer(final int id) {
		return (StreamCodec<ByteStream, T>) byId.get(id);
	}

	public int getSerializedId(final StreamCodec<ByteStream, ?> serializer) {
		final int serializerId = byCodec.getOrDefault(serializer, -1);
		if (serializerId == -1) {
			throw new RuntimeException("Unknown serializer type " + serializer);
		} else {
			return serializerId;
		}
	}
}
