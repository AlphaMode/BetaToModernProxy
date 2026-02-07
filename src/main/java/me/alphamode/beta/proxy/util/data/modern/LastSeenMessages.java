package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.BitSet;
import java.util.Collection;
import java.util.List;

public record LastSeenMessages(List<MessageSignature> signatures) {
	public static LastSeenMessages EMPTY = new LastSeenMessages(List.of());

	public static final StreamCodec<ByteStream, LastSeenMessages> CODEC = StreamCodec.composite(
			ModernStreamCodecs.list(MessageSignature.CODEC),
			LastSeenMessages::signatures,
			LastSeenMessages::new
	);

	public record Update(int offset, BitSet acknowledged, byte checksum) {
		public static final StreamCodec<ByteStream, Update> CODEC = StreamCodec.composite(
				ModernStreamCodecs.VAR_INT,
				Update::offset,
				ModernStreamCodecs.sizedBitSet(20),
				Update::acknowledged,
				CommonStreamCodecs.BYTE,
				Update::checksum,
				Update::new
		);
	}

	public record Packed(Collection<MessageSignature.Packed> entries) {
		public static final StreamCodec<ByteStream, Packed> CODEC = StreamCodec.composite(
				ModernStreamCodecs.collection(MessageSignature.Packed.CODEC),
				Packed::entries,
				Packed::new
		);
	}
}
