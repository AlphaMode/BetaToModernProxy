package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.ChunkPos;

public record S2CForgetLevelChunkPacket(ChunkPos pos) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CForgetLevelChunkPacket> CODEC = StreamCodec.composite(
			ChunkPos.CODEC,
			S2CForgetLevelChunkPacket::pos,
			S2CForgetLevelChunkPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.FORGET_LEVEL_CHUNK;
	}
}
