package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status;

import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundStatusPackets;

public interface C2SStatusPacket extends ModernRecordPacket<ServerboundStatusPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.STATUS;
	}
}
