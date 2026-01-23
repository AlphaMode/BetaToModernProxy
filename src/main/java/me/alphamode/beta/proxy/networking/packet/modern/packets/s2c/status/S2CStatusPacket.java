package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;

public interface S2CStatusPacket extends ModernRecordPacket<ClientboundStatusPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.CLIENTBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.STATUS;
	}
}
