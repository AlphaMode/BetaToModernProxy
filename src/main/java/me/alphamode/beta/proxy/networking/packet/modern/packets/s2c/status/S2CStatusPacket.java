package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status;

import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;

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
