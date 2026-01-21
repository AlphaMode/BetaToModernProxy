package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record LevelEventPacket(int type, Vec3i pos, int data) implements RecordPacket<BetaPackets> {
	public static final StreamCodec<ByteBuf, LevelEventPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			LevelEventPacket::type,
			Vec3i.TINY_CODEC,
			LevelEventPacket::pos,
			ByteBufCodecs.INT,
			LevelEventPacket::data,
			LevelEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.LEVEL_EVENT;
	}
}
