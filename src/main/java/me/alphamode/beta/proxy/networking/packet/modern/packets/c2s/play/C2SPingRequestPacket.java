package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SPingRequestPacket(long time) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SPingRequestPacket> CODEC = StreamCodec.composite(
			BasicCodecs.LONG,
			C2SPingRequestPacket::time,
			C2SPingRequestPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PING_REQUEST;
	}
}
