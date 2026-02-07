package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record InteractionPacket(int id, byte type, Vec3i position) implements BetaPacket {
	public static final StreamCodec<ByteStream, InteractionPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			InteractionPacket::id,
			CommonStreamCodecs.BYTE,
			InteractionPacket::type,
			Vec3i.TINY_CODEC,
			InteractionPacket::position,
			InteractionPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.INTERACTION;
	}
}
