package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BlockHitResult;
import me.alphamode.beta.proxy.util.data.modern.enums.InteractionHand;

public record C2SUseItemOnPacket(InteractionHand hand, BlockHitResult hitResult,
								 int sequence) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SUseItemOnPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.javaEnum(InteractionHand.class),
			C2SUseItemOnPacket::hand,
			BlockHitResult.CODEC,
			C2SUseItemOnPacket::hitResult,
			ModernStreamCodecs.VAR_INT,
			C2SUseItemOnPacket::sequence,
			C2SUseItemOnPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.USE_ITEM_ON;
	}
}
