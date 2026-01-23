package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetTimePacket(long time) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, SetTimePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			SetTimePacket::time,
			SetTimePacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_TIME;
	}
}
