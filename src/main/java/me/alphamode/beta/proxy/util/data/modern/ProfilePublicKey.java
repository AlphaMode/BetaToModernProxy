package me.alphamode.beta.proxy.util.data.modern;

import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.security.PublicKey;
import java.time.Instant;

public record ProfilePublicKey(ProfilePublicKey.Data data) {
	public record Data(Instant expires, PublicKey key, byte[] signature) {
		public static final StreamCodec<ByteStream, Data> CODEC = StreamCodec.composite(
				ModernStreamCodecs.INSTANT,
				Data::expires,
				ModernStreamCodecs.PUBLIC_KEY,
				Data::key,
				ModernStreamCodecs.byteArray(4096),
				Data::signature,
				Data::new
		);
	}
}
