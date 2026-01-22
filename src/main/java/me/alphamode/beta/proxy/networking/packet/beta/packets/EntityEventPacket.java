package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record EntityEventPacket(int entityId, byte eventId) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, EntityEventPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			EntityEventPacket::entityId,
			BasicCodecs.BYTE,
			EntityEventPacket::eventId,
			EntityEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ENTITY_EVENT;
	}
}
