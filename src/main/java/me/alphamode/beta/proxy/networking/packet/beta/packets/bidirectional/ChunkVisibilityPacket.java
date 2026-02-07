package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkVisibilityPacket(int x, int z, boolean visible) implements BetaPacket {
	public static final StreamCodec<ByteStream, ChunkVisibilityPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			ChunkVisibilityPacket::x,
			CommonStreamCodecs.INT,
			ChunkVisibilityPacket::z,
			CommonStreamCodecs.BOOL,
			ChunkVisibilityPacket::visible,
			ChunkVisibilityPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CHUNK_VISIBILITY;
	}
}
