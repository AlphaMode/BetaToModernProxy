package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record LoginPacket(int clientVersion, String username, long seed, byte dimension) implements BetaRecordPacket {
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> USERNAME_CODEC = BetaCodecs.stringUtf8(MAX_USERNAME_LENGTH);
	public static final StreamCodec<ByteBuf, LoginPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			LoginPacket::clientVersion,
			USERNAME_CODEC,
			LoginPacket::username,
			BasicCodecs.LONG,
			LoginPacket::seed,
			BasicCodecs.BYTE,
			LoginPacket::dimension,
			LoginPacket::new
	);

	public LoginPacket(int protocol, String username) {
		this(protocol, username, 0L, (byte) 0);
	}

	@Override
	public BetaPackets getType() {
		return BetaPackets.LOGIN;
	}
}
