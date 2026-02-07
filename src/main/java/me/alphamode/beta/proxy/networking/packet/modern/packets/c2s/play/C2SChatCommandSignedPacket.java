package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ArgumentSignatures;
import me.alphamode.beta.proxy.util.data.modern.LastSeenMessages;

import java.time.Instant;

public record C2SChatCommandSignedPacket(
		String command,
		Instant timeStamp,
		long salt,
		ArgumentSignatures signature,
		LastSeenMessages.Update lastSeenMessages
) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SChatCommandSignedPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.STRING_UTF8,
			C2SChatCommandSignedPacket::command,
			ModernStreamCodecs.INSTANT,
			C2SChatCommandSignedPacket::timeStamp,
			CommonStreamCodecs.LONG,
			C2SChatCommandSignedPacket::salt,
			ArgumentSignatures.CODEC,
			C2SChatCommandSignedPacket::signature,
			LastSeenMessages.Update.CODEC,
			C2SChatCommandSignedPacket::lastSeenMessages,
			C2SChatCommandSignedPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CHAT_COMMAND_SIGNED;
	}
}
