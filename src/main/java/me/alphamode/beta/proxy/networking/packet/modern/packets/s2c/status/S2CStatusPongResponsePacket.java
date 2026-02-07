package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CStatusPongResponsePacket(long time) implements S2CStatusPacket {
	public static final StreamCodec<ByteStream, S2CStatusPongResponsePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			S2CStatusPongResponsePacket::time,
			S2CStatusPongResponsePacket::new
	);

	@Override
	public ClientboundStatusPackets getType() {
		return ClientboundStatusPackets.PONG_RESPONSE;
	}
}
