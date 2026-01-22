package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerAckPacket(short containerId, short uid, boolean accepted) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerAckPacket> CODEC = StreamCodec.composite(
			BasicCodecs.UNSIGNED_BYTE,
			ContainerAckPacket::containerId,
			BasicCodecs.SHORT,
			ContainerAckPacket::uid,
			BasicCodecs.BOOL,
			ContainerAckPacket::accepted,
			ContainerAckPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_ACK;
	}
}
