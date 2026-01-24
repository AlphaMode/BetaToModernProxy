package me.alphamode.beta.proxy.util.data.modern.chat;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.LastSeenMessages;

import java.time.Instant;

public record SignedMessageBody(String content, Instant timeStamp, long salt, LastSeenMessages lastSeen) {
	public static SignedMessageBody unsigned(String content) {
		return new SignedMessageBody(content, Instant.now(), 0L, LastSeenMessages.EMPTY);
	}

	public record Packed(String content, Instant timeStamp, long salt, LastSeenMessages.Packed lastSeen) {
		public static final StreamCodec<ByteBuf, Packed> CODEC = StreamCodec.composite(
				ModernStreamCodecs.stringUtf8(256),
				Packed::content,
				ModernStreamCodecs.INSTANT,
				Packed::timeStamp,
				BasicStreamCodecs.LONG,
				Packed::salt,
				LastSeenMessages.Packed.CODEC,
				Packed::lastSeen,
				Packed::new
		);
	}
}
