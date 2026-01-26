package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerAckPacket(short containerId, short uid, boolean accepted) implements BetaPacket {
	public static final StreamCodec<ByteBuf, ContainerAckPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.UNSIGNED_BYTE,
			ContainerAckPacket::containerId,
			BasicStreamCodecs.SHORT,
			ContainerAckPacket::uid,
			BasicStreamCodecs.BOOL,
			ContainerAckPacket::accepted,
			ContainerAckPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_ACK;
	}
}
