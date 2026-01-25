package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record KeepAlivePacket() implements BetaPacket {
	public static final KeepAlivePacket INSTANCE = new KeepAlivePacket();
	public static final StreamCodec<ByteBuf, KeepAlivePacket> CODEC = StreamCodec.unit(INSTANCE);

	@Override
	public BetaPackets getType() {
		return BetaPackets.KEEP_ALIVE;
	}
}
