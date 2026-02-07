package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetTimePacket(long time) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetTimePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			SetTimePacket::time,
			SetTimePacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_TIME;
	}
}
