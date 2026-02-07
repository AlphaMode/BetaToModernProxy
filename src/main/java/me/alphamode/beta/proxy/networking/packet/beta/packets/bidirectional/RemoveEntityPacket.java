package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record RemoveEntityPacket(int entityId) implements BetaPacket {
	public static final StreamCodec<ByteStream, RemoveEntityPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			RemoveEntityPacket::entityId,
			RemoveEntityPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.REMOVE_ENTITY;
	}
}
