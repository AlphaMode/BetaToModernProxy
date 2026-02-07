package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CHelloPacket(String serverId, byte[] publicKey, byte[] challenge,
							 boolean shouldAuthenticate) implements S2CLoginPacket {
	public static final StreamCodec<ByteStream, S2CHelloPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.stringUtf8(20),
			S2CHelloPacket::serverId,
			ModernStreamCodecs.PREFIXED_BYTE_ARRAY,
			S2CHelloPacket::publicKey,
			ModernStreamCodecs.PREFIXED_BYTE_ARRAY,
			S2CHelloPacket::challenge,
			CommonStreamCodecs.BOOL,
			S2CHelloPacket::shouldAuthenticate,
			S2CHelloPacket::new
	);

	@Override
	public ClientboundLoginPackets getType() {
		return ClientboundLoginPackets.HELLO;
	}
}
