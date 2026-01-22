package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;

public interface C2SConfigurationPacket extends ModernRecordPacket<ServerboundConfigurationPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.CONFIGURATION;
	}
}
