package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record ContainerClickPacket(byte containerId, short slot, byte button, short uid, boolean quickMove,
								   BetaItemStack item) implements BetaPacket {
	public static final StreamCodec<ByteStream, ContainerClickPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.BYTE,
			ContainerClickPacket::containerId,
			CommonStreamCodecs.SHORT,
			ContainerClickPacket::slot,
			CommonStreamCodecs.BYTE,
			ContainerClickPacket::button,
			CommonStreamCodecs.SHORT,
			ContainerClickPacket::uid,
			CommonStreamCodecs.BOOL,
			ContainerClickPacket::quickMove,
			BetaItemStack.OPTIONAL_CODEC,
			ContainerClickPacket::item,
			ContainerClickPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_CLICK;
	}
}
