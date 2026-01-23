package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;

public interface C2SHandshakingPacket extends ModernRecordPacket<ServerboundHandshakingPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.HANDSHAKING;
	}
}
