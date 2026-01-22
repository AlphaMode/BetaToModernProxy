package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddGlobalEntityPacket(int id, byte type, Vec3i positon) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, AddGlobalEntityPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			AddGlobalEntityPacket::id,
			BasicCodecs.BYTE,
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
