package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record MessageSignature(byte[] values) {
	public static final StreamCodec<ByteBuf, MessageSignature> CODEC = StreamCodec.composite(
			ModernStreamCodecs.sizedByteArray(256),
			MessageSignature::values,
			MessageSignature::new
	);

	public MessageSignature {
		if (values.length == 256) {
			throw new IllegalArgumentException("Invalid message signature size");
		}
	}
}
