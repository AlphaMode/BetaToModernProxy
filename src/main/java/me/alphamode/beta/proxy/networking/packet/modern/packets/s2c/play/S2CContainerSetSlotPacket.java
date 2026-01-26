package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ModernItemStack;

public record S2CContainerSetSlotPacket(int containerId, int stateId, short slot,
										ModernItemStack stack) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CContainerSetSlotPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CContainerSetSlotPacket::containerId,
			ModernStreamCodecs.VAR_INT,
			S2CContainerSetSlotPacket::stateId,
			BasicStreamCodecs.SHORT,
			S2CContainerSetSlotPacket::slot,
			ModernItemStack.OPTIONAL_CODEC,
			S2CContainerSetSlotPacket::stack,
			S2CContainerSetSlotPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.CONTAINER_SET_SLOT;
	}
}
