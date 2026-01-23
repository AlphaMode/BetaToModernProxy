package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;

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
