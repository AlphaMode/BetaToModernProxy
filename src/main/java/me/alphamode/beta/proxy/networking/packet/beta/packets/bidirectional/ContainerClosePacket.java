package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerClosePacket(byte containerId) implements BetaPacket {
	public static final StreamCodec<ByteBuf, ContainerClosePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BYTE,
			ContainerClosePacket::containerId,
			ContainerClosePacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.CONTAINER_CLOSE;
	}
}
