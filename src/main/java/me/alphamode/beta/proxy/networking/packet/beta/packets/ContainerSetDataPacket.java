package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerSetDataPacket(short containerId, short id, short value) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, ContainerSetDataPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.UNSIGNED_BYTE,
			ContainerSetDataPacket::containerId,
			ByteBufCodecs.SHORT,
			ContainerSetDataPacket::id,
			ByteBufCodecs.SHORT,
			ContainerSetDataPacket::value,
			ContainerSetDataPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_SET_DATA;
	}
}
