package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;

public interface ModernPacket<T extends Packets> extends RecordPacket<T> {
	PacketDirection getDirection();

	PacketState getState();
}
