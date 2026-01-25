package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;

public interface S2CLoginPacket extends ModernPacket<ClientboundLoginPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.CLIENTBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.LOGIN;
	}
}
