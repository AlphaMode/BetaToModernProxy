package me.alphamode.beta.proxy.util.data.modern.enums;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ByIdMap;

import java.util.function.IntFunction;

public enum ChatVisibility {
	FULL(0),
	SYSTEM(1),
	HIDDEN(2);

	private static final IntFunction<ChatVisibility> BY_ID = ByIdMap.continuous(ChatVisibility::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
	public static final StreamCodec<ByteStream, ChatVisibility> CODEC = ModernStreamCodecs.idMapper(BY_ID, ChatVisibility::getId);
	private final int id;

	ChatVisibility(final int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
