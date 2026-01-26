package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SContainerSlotStateChangedPacket(int slotId, int containerId,
												 boolean newState) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SContainerSlotStateChangedPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SContainerSlotStateChangedPacket::slotId,
			ModernStreamCodecs.VAR_INT,
			C2SContainerSlotStateChangedPacket::containerId,
			BasicStreamCodecs.BOOL,
			C2SContainerSlotStateChangedPacket::newState,
			C2SContainerSlotStateChangedPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CONTAINER_SLOT_STATE_CHANGED;
	}
}
