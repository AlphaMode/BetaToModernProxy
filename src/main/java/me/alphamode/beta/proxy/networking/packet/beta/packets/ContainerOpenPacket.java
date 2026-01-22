package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record ContainerOpenPacket(short containerId, short type, String title, short size) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, ContainerOpenPacket> CODEC = StreamCodec.composite(
			BasicCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::containerId,
			BasicCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::type,
			BetaCodecs.stringJava(),
			ContainerOpenPacket::title,
			BasicCodecs.UNSIGNED_BYTE,
			ContainerOpenPacket::size,
			ContainerOpenPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.CONTAINER_OPEN;
	}
}
