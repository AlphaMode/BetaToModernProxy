package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;

public interface ModernPacket<T extends Packets, S extends PacketState> extends RecordPacket<T> {
	S getState();
}
