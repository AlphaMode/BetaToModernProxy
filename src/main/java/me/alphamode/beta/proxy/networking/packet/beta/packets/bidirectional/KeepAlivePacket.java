package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record KeepAlivePacket() implements BetaPacket {
	public static final KeepAlivePacket INSTANCE = new KeepAlivePacket();
	public static final StreamCodec<ByteStream, KeepAlivePacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.KEEP_ALIVE;
	}
}
