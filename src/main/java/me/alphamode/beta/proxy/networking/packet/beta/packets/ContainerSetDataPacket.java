package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerSetDataPacket(short containerId, short id, short value) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerSetDataPacket> CODEC = StreamCodec.composite(
			BasicCodecs.UNSIGNED_BYTE,
			ContainerSetDataPacket::containerId,
			BasicCodecs.SHORT,
			ContainerSetDataPacket::id,
			BasicCodecs.SHORT,
			ContainerSetDataPacket::value,
			ContainerSetDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_SET_DATA;
	}
}
