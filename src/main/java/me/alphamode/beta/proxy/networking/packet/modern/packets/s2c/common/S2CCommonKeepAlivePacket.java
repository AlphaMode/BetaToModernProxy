package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;

public interface S2CCommonKeepAlivePacket<T extends ModernClientboundPackets> extends S2CCommonPacket<T> {
	long getTime();
}
