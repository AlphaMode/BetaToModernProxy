package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.util.data.modern.ClientInformation;

public interface C2SCommonClientInformationPacket<T extends ModernServerboundPackets> extends C2SCommonPacket<T> {
	ClientInformation getInformation();
}
