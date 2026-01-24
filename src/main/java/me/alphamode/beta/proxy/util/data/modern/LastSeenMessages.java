package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.BitSet;
import java.util.List;

public record LastSeenMessages(List<MessageSignature> signatures) {
	public static LastSeenMessages EMPTY = new LastSeenMessages(List.of());

	public static final StreamCodec<ByteBuf, LastSeenMessages> CODEC = StreamCodec.composite(
			ModernStreamCodecs.list(MessageSignature.CODEC),
			LastSeenMessages::signatures,
			LastSeenMessages::new
	);

	public record Update(int offset, BitSet acknowledged, byte checksum) {
		public static final StreamCodec<ByteBuf, Update> CODEC = StreamCodec.composite(
				ModernStreamCodecs.VAR_INT,
				Update::offset,
				ModernStreamCodecs.sizedBitSet(20),
				Update::acknowledged,
				BasicStreamCodecs.BYTE,
				Update::checksum,
				Update::new
		);
	}

	public record Packed(List<MessageSignature.Packed> entries) {
		public static final StreamCodec<ByteBuf, Packed> CODEC = StreamCodec.composite(
				ModernStreamCodecs.collection(MessageSignature.Packed.CODEC),
				Packed::entries,
				Packed::new
		);
	}
}
