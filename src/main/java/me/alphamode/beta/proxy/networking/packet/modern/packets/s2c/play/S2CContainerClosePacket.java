package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CContainerClosePacket(int containerId) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CContainerClosePacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CContainerClosePacket::containerId,
			S2CContainerClosePacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.CONTAINER_CLOSE;
	}
}
