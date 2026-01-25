package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record LevelEventPacket(int type, Vec3i pos, int data) implements BetaPacket {
	public static final StreamCodec<ByteBuf, LevelEventPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			LevelEventPacket::type,
			Vec3i.TINY_CODEC,
			LevelEventPacket::pos,
			BasicStreamCodecs.INT,
			LevelEventPacket::data,
			LevelEventPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.LEVEL_EVENT;
	}
}
