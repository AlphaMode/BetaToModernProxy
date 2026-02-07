package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.ChunkPos;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLevelChunkPacketData;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLightUpdatePacketData;

public record S2CLevelChunkWithLightPacket(ChunkPos pos, ClientboundLevelChunkPacketData chunkData,
										   ClientboundLightUpdatePacketData lightData) implements S2CPlayPacket {
	public static StreamCodec<ByteStream, S2CLevelChunkWithLightPacket> CODEC = StreamCodec.composite(
			ChunkPos.BASIC_CODEC,
			S2CLevelChunkWithLightPacket::pos,
			ClientboundLevelChunkPacketData.CODEC,
			S2CLevelChunkWithLightPacket::chunkData,
			ClientboundLightUpdatePacketData.CODEC,
			S2CLevelChunkWithLightPacket::lightData,
			S2CLevelChunkWithLightPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.LEVEL_CHUNK_WITH_LIGHT;
	}
}
