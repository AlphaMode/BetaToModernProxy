package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record EntityEventPacket(int entityId, byte eventId) implements BetaPacket {
	public static final StreamCodec<ByteStream, EntityEventPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			EntityEventPacket::entityId,
			CommonStreamCodecs.BYTE,
			EntityEventPacket::eventId,
			EntityEventPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.ENTITY_EVENT;
	}
}
