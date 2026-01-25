package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;

public interface C2SStatusPacket extends ModernPacket<ServerboundStatusPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.STATUS;
	}
}
