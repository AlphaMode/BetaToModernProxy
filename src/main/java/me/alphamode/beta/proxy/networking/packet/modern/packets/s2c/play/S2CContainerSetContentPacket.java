package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.item.ModernItemStack;

import java.util.List;

public record S2CContainerSetContentPacket(int containerId, int stateId, List<ModernItemStack> stacks,
										   ModernItemStack carriedStack) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CContainerSetContentPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CContainerSetContentPacket::containerId,
			ModernStreamCodecs.VAR_INT,
			S2CContainerSetContentPacket::stateId,
			ModernStreamCodecs.list(ModernItemStack.OPTIONAL_CODEC),
			S2CContainerSetContentPacket::stacks,
			ModernItemStack.OPTIONAL_CODEC,
			S2CContainerSetContentPacket::carriedStack,
			S2CContainerSetContentPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.CONTAINER_SET_CONTENT;
	}
}
