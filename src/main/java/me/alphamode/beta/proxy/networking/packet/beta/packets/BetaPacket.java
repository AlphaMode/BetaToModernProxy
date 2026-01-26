package me.alphamode.beta.proxy.networking.packet.beta.packets;

import me.alphamode.beta.proxy.networking.packet.AbstractPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;

public interface BetaPacket extends AbstractPacket<BetaPackets> {
	int PROTOCOL_VERSION = 14;
}
