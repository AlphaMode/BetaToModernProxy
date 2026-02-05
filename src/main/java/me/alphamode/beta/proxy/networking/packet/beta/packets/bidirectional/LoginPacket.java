package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record LoginPacket(int clientVersion, String username, long seed, byte dimension) implements BetaPacket {
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> USERNAME_CODEC = BetaStreamCodecs.stringUtf8(MAX_USERNAME_LENGTH);
	public static final StreamCodec<ByteBuf, LoginPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			LoginPacket::clientVersion,
			USERNAME_CODEC,
			LoginPacket::username,
			BasicStreamCodecs.LONG,
			LoginPacket::seed,
			BasicStreamCodecs.BYTE,
			LoginPacket::dimension,
			LoginPacket::new
	);

	public LoginPacket(final int protocol, final String username) {
		this(protocol, username, 0L, (byte) 0);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.LOGIN;
	}
}
