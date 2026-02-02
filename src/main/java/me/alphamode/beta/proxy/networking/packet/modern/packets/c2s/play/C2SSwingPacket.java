package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.enums.InteractionHand;

public record C2SSwingPacket(InteractionHand hand) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SSwingPacket> CODEC = StreamCodec.composite(
			InteractionHand.CODEC,
			C2SSwingPacket::hand,
			C2SSwingPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.SWING;
	}
}
