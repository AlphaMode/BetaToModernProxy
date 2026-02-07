package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CEntityEventPacket(int entityId, byte eventId) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CEntityEventPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			S2CEntityEventPacket::entityId,
			CommonStreamCodecs.BYTE,
			S2CEntityEventPacket::eventId,
			S2CEntityEventPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.ENTITY_EVENT;
	}
}
