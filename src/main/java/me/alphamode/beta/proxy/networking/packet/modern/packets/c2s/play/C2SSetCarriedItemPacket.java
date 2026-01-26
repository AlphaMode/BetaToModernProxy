package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SSetCarriedItemPacket(short slot) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SSetCarriedItemPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.SHORT,
			C2SSetCarriedItemPacket::slot,
			C2SSetCarriedItemPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.SET_CARRIED_ITEM;
	}
}
