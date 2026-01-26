package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;

public interface C2SLoginPacket extends ModernPacket<ServerboundLoginPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.LOGIN;
	}
}
