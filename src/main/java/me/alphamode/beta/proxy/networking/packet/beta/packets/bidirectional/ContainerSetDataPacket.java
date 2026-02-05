package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerSetDataPacket(short containerId, short id, short value) implements BetaPacket {
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
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_SET_DATA;
	}
}
