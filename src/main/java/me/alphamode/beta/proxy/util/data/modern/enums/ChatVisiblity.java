package me.alphamode.beta.proxy.util.data.modern.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

import java.util.function.IntFunction;

public enum ChatVisiblity {
	FULL(0),
	SYSTEM(1),
	HIDDEN(2);

	private static final IntFunction<ChatVisiblity> BY_ID = ByIdMap.continuous(ChatVisiblity::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
	public static final StreamCodec<ByteBuf, ChatVisiblity> CODEC = ModernStreamCodecs.idMapper(BY_ID, ChatVisiblity::getId);
	private final int id;

	ChatVisiblity(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
