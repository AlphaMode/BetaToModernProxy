package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common;

import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;

public interface C2SCommonPacket<T extends ModernServerboundPackets> extends ModernRecordPacket<T> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}
}
