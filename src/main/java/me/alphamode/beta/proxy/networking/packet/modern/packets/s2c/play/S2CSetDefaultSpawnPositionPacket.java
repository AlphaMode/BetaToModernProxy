package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.LevelData;

public record S2CSetDefaultSpawnPositionPacket(LevelData.RespawnData respawnData) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CSetDefaultSpawnPositionPacket> CODEC = StreamCodec.composite(
			LevelData.RespawnData.CODEC,
			S2CSetDefaultSpawnPositionPacket::respawnData,
			S2CSetDefaultSpawnPositionPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.SET_DEFAULT_SPAWN_POSITION;
	}
}
