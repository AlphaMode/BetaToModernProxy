package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ChatPacket(String message) implements BetaPacket {
	public static final int MAX_CHAT_STRING_LENGTH = 119;
	public static final StreamCodec<ByteBuf, String> CHAT_STRING_CODEC = BetaStreamCodecs.stringUtf8(MAX_CHAT_STRING_LENGTH);
	public static final StreamCodec<ByteBuf, ChatPacket> CODEC = StreamCodec.composite(
			CHAT_STRING_CODEC,
			ChatPacket::message,
			ChatPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CHAT;
	}
}
