package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerClosePacket(byte containerId) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerClosePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			ContainerClosePacket::containerId,
			ContainerClosePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_CLOSE;
	}
}
