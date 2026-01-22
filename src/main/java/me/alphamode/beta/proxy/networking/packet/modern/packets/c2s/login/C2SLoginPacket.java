package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;

public interface C2SLoginPacket extends ModernRecordPacket<ServerboundLoginPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.LOGIN;
	}
}
