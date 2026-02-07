package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record LoginPacket(int clientVersion, String username, long seed, byte dimension) implements BetaPacket {
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final StreamCodec<ByteStream, String> USERNAME_CODEC = BetaStreamCodecs.stringUtf8(MAX_USERNAME_LENGTH);
	public static final StreamCodec<ByteStream, LoginPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			LoginPacket::clientVersion,
			USERNAME_CODEC,
			LoginPacket::username,
			CommonStreamCodecs.LONG,
			LoginPacket::seed,
			CommonStreamCodecs.BYTE,
			LoginPacket::dimension,
			LoginPacket::new
	);

	public LoginPacket(final int protocol, final String username) {
		this(protocol, username, 0L, (byte) 0);
	}

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.LOGIN;
	}
}
