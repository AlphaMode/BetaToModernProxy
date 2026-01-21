package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetTimePacket(long time) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, SetTimePacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.LONG,
			SetTimePacket::time,
			SetTimePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_TIME;
	}
}
