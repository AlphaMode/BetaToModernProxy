package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record SetRidingPacket(int riderId, int riddenId) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetRidingPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			SetRidingPacket::riderId,
			CommonStreamCodecs.INT,
			SetRidingPacket::riddenId,
			SetRidingPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_RIDING;
	}
}
