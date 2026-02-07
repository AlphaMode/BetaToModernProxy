package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record InteractPacket(int attackerId, int targetId, boolean attack) implements BetaPacket {
	public static final StreamCodec<ByteStream, InteractPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			InteractPacket::attackerId,
			CommonStreamCodecs.INT,
			InteractPacket::targetId,
			CommonStreamCodecs.BOOL,
			InteractPacket::attack,
			InteractPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.INTERACT;
	}
}
