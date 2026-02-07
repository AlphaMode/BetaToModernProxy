package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetHealthPacket(short health) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetHealthPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.SHORT,
			SetHealthPacket::health,
			SetHealthPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_HEALTH;
	}
}
