package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record EntityEventPacket(int entityId, byte eventId) implements BetaPacket {
	public static final StreamCodec<ByteBuf, EntityEventPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			EntityEventPacket::entityId,
			BasicStreamCodecs.BYTE,
			EntityEventPacket::eventId,
			EntityEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ENTITY_EVENT;
	}
}
