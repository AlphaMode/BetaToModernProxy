package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerAckPacket(short containerId, short uid, boolean accepted) implements BetaPacket {
	public static final StreamCodec<ByteStream, ContainerAckPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.UNSIGNED_BYTE,
			ContainerAckPacket::containerId,
			CommonStreamCodecs.SHORT,
			ContainerAckPacket::uid,
			CommonStreamCodecs.BOOL,
			ContainerAckPacket::accepted,
			ContainerAckPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_ACK;
	}
}
