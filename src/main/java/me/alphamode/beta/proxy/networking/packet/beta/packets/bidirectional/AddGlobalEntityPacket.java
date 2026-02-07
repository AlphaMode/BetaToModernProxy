package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddGlobalEntityPacket(int id, byte type, Vec3i positon) implements BetaPacket {
	public static final StreamCodec<ByteStream, AddGlobalEntityPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.INT,
			AddGlobalEntityPacket::id,
			CommonStreamCodecs.BYTE,
			AddGlobalEntityPacket::type,
			Vec3i.CODEC,
			AddGlobalEntityPacket::positon,
			AddGlobalEntityPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.ADD_GLOBAL_ENTITY;
	}
}
