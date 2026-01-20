package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerAckPacket(short containerId, short uid, boolean accepted) implements RecordPacket {
	public static final StreamCodec<ByteBuf, ContainerAckPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.UNSIGNED_BYTE,
			ContainerAckPacket::containerId,
			ByteBufCodecs.SHORT,
			ContainerAckPacket::uid,
			ByteBufCodecs.BOOL,
			ContainerAckPacket::accepted,
			ContainerAckPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_ACK;
	}
}
