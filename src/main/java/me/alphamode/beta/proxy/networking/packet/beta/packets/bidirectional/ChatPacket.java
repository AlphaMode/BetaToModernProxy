package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChatPacket(String message) implements BetaPacket {
	public static final int MAX_CHAT_STRING_LENGTH = 119;
	public static final StreamCodec<ByteStream, String> CHAT_STRING_CODEC = BetaStreamCodecs.stringUtf8(MAX_CHAT_STRING_LENGTH);
	public static final StreamCodec<ByteStream, ChatPacket> CODEC = StreamCodec.composite(
			CHAT_STRING_CODEC,
			ChatPacket::message,
			ChatPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CHAT;
	}
}
