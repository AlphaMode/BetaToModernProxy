package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;

public interface C2SPlayPacket extends ModernRecordPacket<ServerboundPlayPackets> {
	@Override
	default PacketDirection getDirection() {
		return PacketDirection.SERVERBOUND;
	}

	@Override
	default PacketState getState() {
		return PacketState.PLAY;
	}
}
