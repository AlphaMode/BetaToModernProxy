package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CBundleDelimiterPacket() implements S2CPlayPacket {
	public static final S2CBundleDelimiterPacket INSTANCE = new S2CBundleDelimiterPacket();
	public static final StreamCodec<ByteStream, S2CBundleDelimiterPacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.BUNDLE_DELIMITER;
	}
}
