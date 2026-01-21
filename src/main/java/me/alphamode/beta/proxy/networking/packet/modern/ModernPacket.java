package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.RecordPacket;

public interface ModernPacket<T, V extends PacketDirection, S extends PacketState> extends RecordPacket<T> {
	V getDirection();

	S getState();
}
