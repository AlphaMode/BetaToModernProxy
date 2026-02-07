package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.enums.InteractionHand;

public record C2SUseItemPacket(InteractionHand hand, int sequence, float yRot, float xRot) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SUseItemPacket> CODEC = StreamCodec.composite(
			InteractionHand.CODEC,
			C2SUseItemPacket::hand,
			ModernStreamCodecs.VAR_INT,
			C2SUseItemPacket::sequence,
			CommonStreamCodecs.FLOAT,
			C2SUseItemPacket::yRot,
			CommonStreamCodecs.FLOAT,
			C2SUseItemPacket::xRot,
			C2SUseItemPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.USE_ITEM;
	}
}
