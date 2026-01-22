package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record HandshakePacket(String username) implements BetaRecordPacket {
	public static final int MAX_USERNAME_LENGTH = 32;
	public static final StreamCodec<ByteBuf, String> USERNAME_CODEC = BetaCodecs.stringUtf8(MAX_USERNAME_LENGTH);
	public static final StreamCodec<ByteBuf, HandshakePacket> CODEC = StreamCodec.composite(
			USERNAME_CODEC,
			HandshakePacket::username,
			HandshakePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.HANDSHAKE;
	}
}
