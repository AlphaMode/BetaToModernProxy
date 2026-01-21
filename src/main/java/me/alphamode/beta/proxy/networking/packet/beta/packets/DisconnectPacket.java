package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record DisconnectPacket(String reason) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, DisconnectPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.stringUtf8(100),
			DisconnectPacket::reason,
			DisconnectPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.DISCONNECT;
	}
}
