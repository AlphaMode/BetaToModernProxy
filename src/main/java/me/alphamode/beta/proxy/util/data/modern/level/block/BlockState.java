package me.alphamode.beta.proxy.util.data.modern.level.block;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockState(int networkId) {
	public static final StreamCodec<ByteStream, BlockState> STREAM_CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT, BlockState::networkId,
			BlockState::new
	);

	public boolean isAir() {
		return networkId == 0;
	}
}
