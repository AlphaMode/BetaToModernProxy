package me.alphamode.beta.proxy.networking.packet;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PacketRegistry<V extends Packets, K extends AbstractPacket<V>> {
	protected final Map<V, StreamCodec<ByteStream, ? extends AbstractPacket<?>>> registry = new ConcurrentHashMap<>();

	public abstract K createPacket(final int packetId, final PacketDirection direction, final PacketState state, final ByteStream buf);

	public K createPacket(final int packetId, final ByteStream buf) {
		return createPacket(packetId, PacketDirection.SERVERBOUND, PacketState.PLAY, buf);
	}

	public <T extends AbstractPacket<V>> StreamCodec<ByteStream, T> getCodec(final V type) {
		if (!registry.containsKey(type)) {
			throw new IllegalArgumentException("Packet type " + type + " is not registered in the packet registry");
		} else {
			return (StreamCodec<ByteStream, T>) this.registry.get(type);
		}
	}

	protected final <S extends V> void registerPacket(final S packetType, final StreamCodec<ByteStream, ? extends AbstractPacket<S>> packetCreator) {
		if (packetCreator != null) {
			this.registry.put(packetType, packetCreator);
		}
	}
}
