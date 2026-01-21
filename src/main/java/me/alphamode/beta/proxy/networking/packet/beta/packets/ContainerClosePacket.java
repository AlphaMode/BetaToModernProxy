package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerClosePacket(byte containerId) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, ContainerClosePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.BYTE,
			ContainerClosePacket::containerId,
			ContainerClosePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_CLOSE;
	}
}
