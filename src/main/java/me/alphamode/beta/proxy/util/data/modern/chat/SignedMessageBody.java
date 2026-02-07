package me.alphamode.beta.proxy.util.data.modern.chat;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.LastSeenMessages;

import java.time.Instant;

public record SignedMessageBody(String content, Instant timeStamp, long salt, LastSeenMessages lastSeen) {
	public static SignedMessageBody unsigned(String content) {
		return new SignedMessageBody(content, Instant.now(), 0L, LastSeenMessages.EMPTY);
	}

	public record Packed(String content, Instant timeStamp, long salt, LastSeenMessages.Packed lastSeen) {
		public static final int MAX_CONTENT_LENGTH = 256;
		public static final StreamCodec<ByteStream, String> CONTENT_CODEC = ModernStreamCodecs.stringUtf8(MAX_CONTENT_LENGTH);
		public static final StreamCodec<ByteStream, Packed> CODEC = StreamCodec.composite(
				CONTENT_CODEC,
				Packed::content,
				ModernStreamCodecs.INSTANT,
				Packed::timeStamp,
				CommonStreamCodecs.LONG,
				Packed::salt,
				LastSeenMessages.Packed.CODEC,
				Packed::lastSeen,
				Packed::new
		);
	}
}
