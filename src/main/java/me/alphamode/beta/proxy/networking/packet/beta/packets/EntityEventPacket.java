package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record EntityEventPacket(int entityId, byte eventId) implements RecordPacket {
	public static final StreamCodec<ByteBuf, EntityEventPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			EntityEventPacket::entityId,
			ByteBufCodecs.BYTE,
			EntityEventPacket::eventId,
			EntityEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ENTITY_EVENT;
	}
}
