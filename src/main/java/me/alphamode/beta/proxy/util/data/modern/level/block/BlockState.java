package me.alphamode.beta.proxy.util.data.modern.level.block;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record BlockState(int networkId) {
	public static final StreamCodec<ByteBuf, BlockState> STREAM_CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT, BlockState::networkId,
			BlockState::new
	);

	public boolean isAir() {
		return networkId == 0;
	}
}
