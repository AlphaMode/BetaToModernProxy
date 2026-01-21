package me.alphamode.beta.proxy.util.data;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record NibbleArray(byte[] data) {
	public static final StreamCodec<ByteBuf, NibbleArray> COMPRESSED_CODEC = StreamCodec.composite(
			ByteBufCodecs.COMPRESSED_BYTE_ARRAY,
			NibbleArray::data,
			NibbleArray::new
	);

	public static final StreamCodec<ByteBuf, NibbleArray> CODEC = StreamCodec.composite(
			ByteBufCodecs.BYTE_ARRAY,
			NibbleArray::data,
			NibbleArray::new
	);

	public byte get(int x, int y, int z) {
		return 0;
	}

	public void set(int x, int y, int z, int value) {
	}
}
