package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.List;

public record ArgumentSignatures(List<Entry> entries) {
	public static final StreamCodec<ByteStream, ArgumentSignatures> CODEC = StreamCodec.composite(
			ModernStreamCodecs.list(Entry.CODEC),
			ArgumentSignatures::entries,
			ArgumentSignatures::new
	);

	public record Entry(String name, MessageSignature signature) {
		public static final int MAX_NAME_LENGTH = 16;
		public static final StreamCodec<ByteStream, String> NAME_CODEC = ModernStreamCodecs.stringUtf8(MAX_NAME_LENGTH);
		public static final StreamCodec<ByteStream, Entry> CODEC = StreamCodec.composite(
				NAME_CODEC,
				Entry::name,
				MessageSignature.CODEC,
				Entry::signature,
				Entry::new
		);
	}
}
