package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CSetChunkCacheRadiusPacket(int radius) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CSetChunkCacheRadiusPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CSetChunkCacheRadiusPacket::radius,
			S2CSetChunkCacheRadiusPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_CHUNK_CACHE_RADIUS;
	}
}
