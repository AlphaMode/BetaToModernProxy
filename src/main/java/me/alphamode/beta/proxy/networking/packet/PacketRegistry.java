package me.alphamode.beta.proxy.networking.packet;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PacketRegistry<V extends Packets, K extends AbstractPacket<V>> {
	protected final Map<V, StreamCodec<ByteBuf, ? extends AbstractPacket<?>>> registry = new ConcurrentHashMap<>();

	public abstract K createPacket(final int packetId, final PacketDirection direction, final PacketState state, final ByteBuf byteBuf);

	public <T extends AbstractPacket<V>> StreamCodec<ByteBuf, T> getCodec(final V type) {
		if (!registry.containsKey(type)) {
			throw new IllegalArgumentException("Packet type " + type + " is not registered in the packet registry");
		} else {
			return (StreamCodec<ByteBuf, T>) this.registry.get(type);
		}
	}

	protected final <S extends V> void registerPacket(final S packetType, final StreamCodec<ByteBuf, ? extends AbstractPacket<S>> packetCreator) {
		if (packetCreator != null) {
			this.registry.put(packetType, packetCreator);
		}
	}
}
