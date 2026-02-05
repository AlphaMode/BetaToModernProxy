package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;

public record ContainerClickPacket(byte containerId, short slot, byte button, short uid, boolean quickMove,
								   BetaItemStack item) implements BetaPacket {
	public static final StreamCodec<ByteBuf, ContainerClickPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			ContainerClickPacket::containerId,
			BasicStreamCodecs.SHORT,
			ContainerClickPacket::slot,
			BasicStreamCodecs.BYTE,
			ContainerClickPacket::button,
			BasicStreamCodecs.SHORT,
			ContainerClickPacket::uid,
			BasicStreamCodecs.BOOL,
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
