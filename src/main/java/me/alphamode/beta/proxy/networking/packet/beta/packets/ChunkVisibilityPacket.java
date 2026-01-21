package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkVisibilityPacket(int x, int z, boolean visible) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, ChunkVisibilityPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			ChunkVisibilityPacket::x,
			ByteBufCodecs.INT,
			ChunkVisibilityPacket::z,
			ByteBufCodecs.BOOL,
			ChunkVisibilityPacket::visible,
			ChunkVisibilityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CHUNK_VISIBILITY;
	}
}
