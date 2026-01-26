package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;

public interface C2SCommonPacket<T extends ModernServerboundPackets> extends ModernPacket<T> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}
}
