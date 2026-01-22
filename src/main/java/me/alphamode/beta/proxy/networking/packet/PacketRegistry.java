package me.alphamode.beta.proxy.networking.packet;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PacketRegistry<V extends Packets> {
	protected final Map<V, StreamCodec<ByteBuf, ? extends RecordPacket<?>>> registry = new ConcurrentHashMap<>();

	public abstract RecordPacket<V> createPacket(int packetId, final ByteBuf byteBuf);

	public <T extends RecordPacket<V>> StreamCodec<ByteBuf, T> getCodec(final V type) {
		if (!registry.containsKey(type)) {
			throw new IllegalArgumentException("Packet type " + type + " is not registered in the packet registry");
		} else {
			return (StreamCodec<ByteBuf, T>) this.registry.get(type);
		}
	}

	protected final void registerPacket(final V packetType, final StreamCodec<ByteBuf, ? extends RecordPacket<?>> packetCreator) {
		this.registry.put(packetType, packetCreator);
	}
}