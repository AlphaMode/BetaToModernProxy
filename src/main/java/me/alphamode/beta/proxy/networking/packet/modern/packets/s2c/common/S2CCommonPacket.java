package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;

public interface S2CCommonPacket<T extends ModernClientboundPackets> extends ModernPacket<T> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.CLIENTBOUND;
	}
}
