package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;

public class S2CSetChunkCacheRadiusPacket implements S2CPlayPacket {
	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_CHUNK_CACHE_RADIUS;
	}
}
