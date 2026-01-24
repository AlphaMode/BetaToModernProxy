package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;

public record S2CLevelChunkPacketData() implements S2CPlayPacket {
	@Override
	public ClientboundPlayPackets getType() {
		return null;
	}
}
