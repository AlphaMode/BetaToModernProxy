package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.UUID;

public record C2SHelloPacket(String username, UUID profileId) implements C2SLoginPacket {
	public static final int MAX_USERNAME_LENGTH = 16;
	public static final StreamCodec<ByteStream, String> USERNAME_CODEC = ModernStreamCodecs.stringUtf8(MAX_USERNAME_LENGTH);
	public static final StreamCodec<ByteStream, C2SHelloPacket> CODEC = StreamCodec.composite(
			USERNAME_CODEC,
			C2SHelloPacket::username,
			ModernStreamCodecs.UUID,
			C2SHelloPacket::profileId,
			C2SHelloPacket::new
	);

	@Override
	public ServerboundLoginPackets getType() {
		return ServerboundLoginPackets.HELLO;
	}
}
