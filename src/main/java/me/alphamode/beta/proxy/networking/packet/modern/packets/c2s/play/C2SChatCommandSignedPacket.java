package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
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
	public static final StreamCodec<ByteBuf, C2SChatCommandSignedPacket> CODEC = StreamCodec.composite(
			ModernCodecs.stringUtf8(),
			C2SChatCommandSignedPacket::command,
			ModernCodecs.INSTANT,
			C2SChatCommandSignedPacket::timeStamp,
			BasicCodecs.LONG,
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
