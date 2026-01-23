package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.UUID;

public record C2SHelloPacket(String username, UUID profileId) implements C2SLoginPacket {
	public static final StreamCodec<ByteBuf, C2SHelloPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.stringUtf8(16),
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
