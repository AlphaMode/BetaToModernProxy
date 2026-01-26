package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record SetSpawnPositionPacket(Vec3i position) implements BetaPacket {
	public static final StreamCodec<ByteBuf, SetSpawnPositionPacket> CODEC = StreamCodec.composite(
			Vec3i.CODEC,
			SetSpawnPositionPacket::position,
			SetSpawnPositionPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.SET_SPAWN_POSITION;
	}
}
