package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddGlobalEntityPacket(int id, byte type, Vec3i positon) implements RecordPacket {
	public static final StreamCodec<ByteBuf, AddGlobalEntityPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			AddGlobalEntityPacket::id,
			ByteBufCodecs.BYTE,
			AddGlobalEntityPacket::type,
			Vec3i.CODEC,
			AddGlobalEntityPacket::positon,
			AddGlobalEntityPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_GLOBAL_ENTITY;
	}
}
