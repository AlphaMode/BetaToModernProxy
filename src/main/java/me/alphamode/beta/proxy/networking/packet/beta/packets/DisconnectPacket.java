package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record DisconnectPacket(String reason) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, DisconnectPacket> CODEC = StreamCodec.composite(
			BetaCodecs.stringUtf8(100),
			DisconnectPacket::reason,
			DisconnectPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.DISCONNECT;
	}
}
