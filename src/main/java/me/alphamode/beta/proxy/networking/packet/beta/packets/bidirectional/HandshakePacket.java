package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record HandshakePacket(String username) implements BetaPacket {
	public static final int MAX_USERNAME_LENGTH = 32;
	public static final StreamCodec<ByteStream, String> USERNAME_CODEC = BetaStreamCodecs.stringUtf8(MAX_USERNAME_LENGTH);
	public static final StreamCodec<ByteStream, HandshakePacket> CODEC = StreamCodec.composite(
			USERNAME_CODEC,
			HandshakePacket::username,
			HandshakePacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.HANDSHAKE;
	}
}
