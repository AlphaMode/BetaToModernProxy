package me.alphamode.beta.proxy.networking.packet.modern.packets;

import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.Packets;

public interface ModernPacket<T extends Packets> extends AbstractPacket<T> {
	int PROTOCOL_VERSION = 774;

	PacketDirection getDirection();

	PacketState getState();
}
