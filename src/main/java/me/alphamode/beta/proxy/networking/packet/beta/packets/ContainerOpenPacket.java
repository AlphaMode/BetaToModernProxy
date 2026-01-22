package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerOpenPacket(short containerId, short type, String title, short size) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerOpenPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::containerId,
			ByteBufCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::type,
			ByteBufCodecs.stringJava(),
			ContainerOpenPacket::title,
			ByteBufCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::size,
			ContainerOpenPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_OPEN;
	}
}
