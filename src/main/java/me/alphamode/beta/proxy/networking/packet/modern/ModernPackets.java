package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.util.Identifier;

public interface ModernPackets extends Packets {
	Identifier getIdentifier();

	PacketState getState();
}
