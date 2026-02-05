package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChunkVisibilityPacket(int x, int z, boolean visible) implements BetaPacket {
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
	public BetaPacketType getType() {
		return BetaPacketType.CHUNK_VISIBILITY;
	}
}
