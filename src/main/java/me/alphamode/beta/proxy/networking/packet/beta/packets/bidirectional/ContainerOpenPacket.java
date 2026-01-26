package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerOpenPacket(short containerId, short type, String title, short size) implements BetaPacket {
	public static final StreamCodec<ByteBuf, ContainerOpenPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::containerId,
			BasicStreamCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::type,
			BetaStreamCodecs.stringJava(),
			ContainerOpenPacket::title,
			BasicStreamCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::size,
			ContainerOpenPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_OPEN;
	}
}
