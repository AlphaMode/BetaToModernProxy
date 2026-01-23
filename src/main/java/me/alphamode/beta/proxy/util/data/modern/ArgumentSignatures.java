package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.List;

public record ArgumentSignatures(List<Entry> entries) {
	public static final StreamCodec<ByteBuf, ArgumentSignatures> CODEC = StreamCodec.composite(
			ModernStreamCodecs.list(Entry.CODEC),
			ArgumentSignatures::entries,
			ArgumentSignatures::new
	);

	public record Entry(String name, MessageSignature signature) {
		public static final StreamCodec<ByteBuf, Entry> CODEC = StreamCodec.composite(
				ModernStreamCodecs.stringUtf8(16),
				Entry::name,
				MessageSignature.CODEC,
				Entry::signature,
				Entry::new
		);
	}
}
