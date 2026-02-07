package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SContainerClosePacket(int containerId) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SContainerClosePacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SContainerClosePacket::containerId,
			C2SContainerClosePacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CONTAINER_CLOSE;
	}
}
