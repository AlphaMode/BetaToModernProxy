package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record LevelEventPacket(int type, Vec3i pos, int data) implements BetaPacket {
	public static final StreamCodec<ByteStream, LevelEventPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			LevelEventPacket::type,
			Vec3i.TINY_CODEC,
			LevelEventPacket::pos,
			CommonStreamCodecs.INT,
			LevelEventPacket::data,
			LevelEventPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.LEVEL_EVENT;
	}
}
