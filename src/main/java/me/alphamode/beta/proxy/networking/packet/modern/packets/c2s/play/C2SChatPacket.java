package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.LastSeenMessages;
import me.alphamode.beta.proxy.util.data.modern.MessageSignature;

import java.time.Instant;

public record C2SChatPacket(
		String message,
		Instant timeStamp,
		long salt,
		MessageSignature signature,
		LastSeenMessages.Update lastSeenMessages
) implements C2SPlayPacket {
	public static final int MAX_MESSAGE_LENGTH = 256;
	public static final StreamCodec<ByteStream, String> MESSAGE_CODEC = ModernStreamCodecs.stringUtf8(MAX_MESSAGE_LENGTH);
	public static final StreamCodec<ByteStream, C2SChatPacket> CODEC = StreamCodec.composite(
			MESSAGE_CODEC,
			C2SChatPacket::message,
			ModernStreamCodecs.INSTANT,
			C2SChatPacket::timeStamp,
			CommonStreamCodecs.LONG,
			C2SChatPacket::salt,
			ModernStreamCodecs.nullable(MessageSignature.CODEC),
			C2SChatPacket::signature,
			LastSeenMessages.Update.CODEC,
			C2SChatPacket::lastSeenMessages,
			C2SChatPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CHAT;
	}
}
