package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record ContainerSetSlotPacket(byte containerId, short slot, BetaItemStack item) implements BetaPacket {
	public static final StreamCodec<ByteStream, ContainerSetSlotPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.BYTE,
			ContainerSetSlotPacket::containerId,
			CommonStreamCodecs.SHORT,
			ContainerSetSlotPacket::slot,
			BetaItemStack.OPTIONAL_CODEC,
			ContainerSetSlotPacket::item,
			ContainerSetSlotPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_SET_SLOT;
	}
}
