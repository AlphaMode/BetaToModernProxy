package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.BlockPos;
import me.alphamode.beta.proxy.util.data.modern.level.block.BlockState;

public record S2CBlockUpdatePacket(BlockPos pos, BlockState state) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CBlockUpdatePacket> CODEC = StreamCodec.composite(
			BlockPos.CODEC,
			S2CBlockUpdatePacket::pos,
			BlockState.STREAM_CODEC,
			S2CBlockUpdatePacket::state,
			S2CBlockUpdatePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.BLOCK_UPDATE;
	}
}
