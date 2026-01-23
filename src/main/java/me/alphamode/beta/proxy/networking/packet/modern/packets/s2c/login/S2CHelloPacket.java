package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CHelloPacket(String serverId, byte[] publicKey, byte[] challenge,
							 boolean shouldAuthenticate) implements S2CLoginPacket {
	public static final StreamCodec<ByteBuf, S2CHelloPacket> CODEC = StreamCodec.composite(
			ModernCodecs.stringUtf8(20),
			S2CHelloPacket::serverId,
			ModernCodecs.PREFIXED_BYTE_ARRAY,
			S2CHelloPacket::publicKey,
			ModernCodecs.PREFIXED_BYTE_ARRAY,
			S2CHelloPacket::challenge,
			BasicCodecs.BOOL,
			S2CHelloPacket::shouldAuthenticate,
			S2CHelloPacket::new
	);

	@Override
	public ClientboundLoginPackets getType() {
		return ClientboundLoginPackets.HELLO;
	}
}
