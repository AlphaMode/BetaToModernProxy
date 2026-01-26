package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;

public interface C2SHandshakingPacket extends ModernPacket<ServerboundHandshakingPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.HANDSHAKING;
	}
}
