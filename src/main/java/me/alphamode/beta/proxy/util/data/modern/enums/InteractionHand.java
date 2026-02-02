package me.alphamode.beta.proxy.util.data.modern.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public enum InteractionHand {
	MAIN_HAND,
	OFF_HAND;

	public static final StreamCodec<ByteBuf, InteractionHand> CODEC = ModernStreamCodecs.javaEnum(InteractionHand.class);
}
