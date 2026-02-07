package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SChatCommandPacket(String command) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SChatCommandPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.STRING_UTF8,
			C2SChatCommandPacket::command,
			C2SChatCommandPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CHAT_COMMAND;
	}
}
