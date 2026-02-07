package me.alphamode.beta.proxy.util.data.modern.enums;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public enum InteractionHand {
	MAIN_HAND,
	OFF_HAND;

	public static final StreamCodec<ByteStream, InteractionHand> CODEC = ModernStreamCodecs.javaEnum(InteractionHand.class);
}
