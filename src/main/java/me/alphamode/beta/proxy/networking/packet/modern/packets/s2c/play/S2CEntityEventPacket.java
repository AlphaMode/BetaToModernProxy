package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CEntityEventPacket(int entityId, byte eventId) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CEntityEventPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			S2CEntityEventPacket::entityId,
			BasicStreamCodecs.BYTE,
			S2CEntityEventPacket::eventId,
			S2CEntityEventPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.ENTITY_EVENT;
	}
}
