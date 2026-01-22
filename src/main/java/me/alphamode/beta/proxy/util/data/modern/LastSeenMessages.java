package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.BitSet;
import java.util.List;

public record LastSeenMessages(List<MessageSignature> signatures) {
	public static final StreamCodec<ByteBuf, LastSeenMessages> CODEC = StreamCodec.composite(
			ModernCodecs.list(MessageSignature.CODEC),
			LastSeenMessages::signatures,
			LastSeenMessages::new
	);

	public record Update(int offset, BitSet acknowledged, byte checksum) {
		public static final StreamCodec<ByteBuf, Update> CODEC = StreamCodec.composite(
				ModernCodecs.VAR_INT,
				Update::offset,
				ModernCodecs.sizedBitSet(20),
				Update::acknowledged,
				BasicCodecs.BYTE,
				Update::checksum,
				Update::new
		);
	}
}
