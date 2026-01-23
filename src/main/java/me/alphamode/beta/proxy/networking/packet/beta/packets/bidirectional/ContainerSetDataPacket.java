package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerSetDataPacket(short containerId, short id, short value) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerSetDataPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.UNSIGNED_BYTE,
			ContainerSetDataPacket::containerId,
			BasicStreamCodecs.SHORT,
			ContainerSetDataPacket::id,
			BasicStreamCodecs.SHORT,
			ContainerSetDataPacket::value,
			ContainerSetDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_SET_DATA;
	}
}
