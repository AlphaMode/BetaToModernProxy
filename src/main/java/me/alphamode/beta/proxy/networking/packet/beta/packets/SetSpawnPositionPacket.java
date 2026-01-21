package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record SetSpawnPositionPacket(Vec3i position) implements RecordPacket<BetaPackets> {
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
