package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;

public record ContainerSetSlotPacket(byte containerId, short slot, BetaItemStack item) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerSetSlotPacket> CODEC = StreamCodec.composite(
			BasicCodecs.BYTE,
			ContainerSetSlotPacket::containerId,
			BasicCodecs.SHORT,
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
