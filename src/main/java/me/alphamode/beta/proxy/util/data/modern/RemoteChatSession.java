package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.UUID;

public record RemoteChatSession(UUID sessionId, ProfilePublicKey key) {
	public record Data(UUID sessionId, ProfilePublicKey.Data key) {
		public static final StreamCodec<ByteStream, Data> CODEC = StreamCodec.composite(
				ModernStreamCodecs.UUID,
				Data::sessionId,
				ProfilePublicKey.Data.CODEC,
				Data::key,
				Data::new
		);
	}
}
