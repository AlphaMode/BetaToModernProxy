package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkVisibilityPacket(int x, int z, boolean visible) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ChunkVisibilityPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			ChunkVisibilityPacket::x,
			BasicStreamCodecs.INT,
			ChunkVisibilityPacket::z,
			BasicStreamCodecs.BOOL,
			ChunkVisibilityPacket::visible,
			ChunkVisibilityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CHUNK_VISIBILITY;
	}
}
