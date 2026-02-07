package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerSetDataPacket(short containerId, short id, short value) implements BetaPacket {
	public static final StreamCodec<ByteStream, ContainerSetDataPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.UNSIGNED_BYTE,
			ContainerSetDataPacket::containerId,
			CommonStreamCodecs.SHORT,
			ContainerSetDataPacket::id,
			CommonStreamCodecs.SHORT,
			ContainerSetDataPacket::value,
			ContainerSetDataPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_SET_DATA;
	}
}
