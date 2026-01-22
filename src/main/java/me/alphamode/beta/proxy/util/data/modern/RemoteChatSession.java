package me.alphamode.beta.proxy.util.data.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.UUID;

public record RemoteChatSession(UUID sessionId, ProfilePublicKey key) {
	public record Data(UUID sessionId, ProfilePublicKey.Data key) {
		public static final StreamCodec<ByteBuf, Data> CODEC = StreamCodec.composite(
				ModernCodecs.UUID,
				Data::sessionId,
				ProfilePublicKey.Data.CODEC,
				Data::key,
				Data::new
		);
	}
}
