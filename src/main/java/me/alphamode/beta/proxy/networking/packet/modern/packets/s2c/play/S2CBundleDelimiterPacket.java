package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CBundleDelimiterPacket() implements S2CPlayPacket {
	public static final S2CBundleDelimiterPacket INSTANCE = new S2CBundleDelimiterPacket();
	public static final StreamCodec<ByteBuf, S2CBundleDelimiterPacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.BUNDLE_DELIMITER;
	}
}
