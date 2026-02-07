package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record SetSpawnPositionPacket(Vec3i position) implements BetaPacket {
	public static final StreamCodec<ByteStream, SetSpawnPositionPacket> CODEC = StreamCodec.composite(
			Vec3i.CODEC,
			SetSpawnPositionPacket::position,
			SetSpawnPositionPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.SET_SPAWN_POSITION;
	}
}
