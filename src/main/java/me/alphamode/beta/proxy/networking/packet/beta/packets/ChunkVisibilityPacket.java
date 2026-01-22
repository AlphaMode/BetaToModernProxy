package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkVisibilityPacket(int x, int z, boolean visible) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ChunkVisibilityPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			ChunkVisibilityPacket::x,
			BasicCodecs.INT,
			ChunkVisibilityPacket::z,
			BasicCodecs.BOOL,
			ChunkVisibilityPacket::visible,
			ChunkVisibilityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CHUNK_VISIBILITY;
	}
}
