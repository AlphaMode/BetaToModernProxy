package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public interface C2SCommonCustomPayloadPacket<T extends ModernServerboundPackets> extends C2SCommonPacket<T> {
	Identifier getIdentifier();

	byte[] getData();
}
