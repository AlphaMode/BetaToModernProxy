package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;

public class S2CLevelChunkWithLightPacket implements S2CPlayPacket {
	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.LEVEL_CHUNK_WITH_LIGHT;
	}
}
