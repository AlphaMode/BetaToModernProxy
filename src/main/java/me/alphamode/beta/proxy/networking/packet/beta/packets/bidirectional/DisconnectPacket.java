package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record DisconnectPacket(String reason) implements BetaPacket {
	public static final StreamCodec<ByteBuf, DisconnectPacket> CODEC = StreamCodec.composite(
			BetaStreamCodecs.stringUtf8(100),
			DisconnectPacket::reason,
			DisconnectPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.DISCONNECT;
	}
}
