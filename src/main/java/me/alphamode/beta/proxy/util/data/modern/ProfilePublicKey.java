package me.alphamode.beta.proxy.util.data.modern;

import java.security.PublicKey;
import java.time.Instant;

public record ProfilePublicKey(ProfilePublicKey.Data data) {
	public record Data(Instant expiresAt, PublicKey key, byte[] keySignature) {
	}
}
