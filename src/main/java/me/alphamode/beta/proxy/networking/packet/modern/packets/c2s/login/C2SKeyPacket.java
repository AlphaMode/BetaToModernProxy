package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.raphimc.netminecraft.netty.crypto.CryptUtil;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;

public record C2SKeyPacket(byte[] keyBytes, byte[] encryptedChallenge) implements C2SLoginPacket {
	public static final StreamCodec<ByteStream, C2SKeyPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.PREFIXED_BYTE_ARRAY,
			C2SKeyPacket::keyBytes,
			ModernStreamCodecs.PREFIXED_BYTE_ARRAY,
			C2SKeyPacket::encryptedChallenge,
			C2SKeyPacket::new
	);

	public C2SKeyPacket(final SecretKey secretKey, final PublicKey publicKey, final byte[] challenge) {
		this(CryptUtil.encryptData(publicKey, secretKey.getEncoded()), CryptUtil.encryptData(publicKey, challenge));
	}

	@Override
	public ServerboundLoginPackets getType() {
		return ServerboundLoginPackets.KEY;
	}

	public SecretKey getSecretKey(final PrivateKey privateKey) {
		return CryptUtil.decryptSecretKey(privateKey, this.keyBytes());
	}

	public boolean isChallengeValid(final byte[] challenge, final PrivateKey privateKey) {
		return Arrays.equals(challenge, CryptUtil.decryptData(privateKey, this.encryptedChallenge));
	}
}
