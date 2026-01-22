package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;

public record ContainerClickPacket(byte containerId, short slot, byte button, short uid, boolean quickMove,
								   BetaItemStack item) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerClickPacket> CODEC = StreamCodec.composite(
			BasicCodecs.BYTE,
			ContainerClickPacket::containerId,
			BasicCodecs.SHORT,
			ContainerClickPacket::slot,
			BasicCodecs.BYTE,
			ContainerClickPacket::button,
			BasicCodecs.SHORT,
			ContainerClickPacket::uid,
			BasicCodecs.BOOL,
			ContainerClickPacket::quickMove,
			BetaItemStack.OPTIONAL_CODEC,
			ContainerClickPacket::item,
			ContainerClickPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_CLICK;
	}
}
