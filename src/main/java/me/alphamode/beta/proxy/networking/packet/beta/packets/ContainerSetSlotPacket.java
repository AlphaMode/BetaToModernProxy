package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.BetaItemStack;

public record ContainerSetSlotPacket(byte containerId, short slot, BetaItemStack item) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, ContainerSetSlotPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.BYTE,
			ContainerSetSlotPacket::containerId,
			ByteBufCodecs.SHORT,
			ContainerSetSlotPacket::slot,
			BetaItemStack.OPTIONAL_CODEC,
			ContainerSetSlotPacket::item,
			ContainerSetSlotPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_SET_SLOT;
	}
}
