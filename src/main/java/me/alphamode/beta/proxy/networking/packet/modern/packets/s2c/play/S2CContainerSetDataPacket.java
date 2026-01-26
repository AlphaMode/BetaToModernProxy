package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CContainerSetDataPacket(int containerId, short id, short value) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CContainerSetDataPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CContainerSetDataPacket::containerId,
			BasicStreamCodecs.SHORT,
			S2CContainerSetDataPacket::id,
			BasicStreamCodecs.SHORT,
			S2CContainerSetDataPacket::value,
			S2CContainerSetDataPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.CONTAINER_SET_DATA;
	}
}
