package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.CommonPlayerSpawnInfo;

public record S2CRespawnPacket(CommonPlayerSpawnInfo commonPlayerSpawnInfo, byte dataToKeep) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CRespawnPacket> CODEC = StreamCodec.composite(
			CommonPlayerSpawnInfo.CODEC,
			S2CRespawnPacket::commonPlayerSpawnInfo,
			BasicStreamCodecs.BYTE,
			S2CRespawnPacket::dataToKeep,
			S2CRespawnPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.RESPAWN;
	}
}
