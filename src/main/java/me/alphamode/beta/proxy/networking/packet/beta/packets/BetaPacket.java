package me.alphamode.beta.proxy.networking.packet.beta.packets;

import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;

public interface BetaPacket extends AbstractPacket<BetaPacketType> {
	int PROTOCOL_VERSION = 14;
}
