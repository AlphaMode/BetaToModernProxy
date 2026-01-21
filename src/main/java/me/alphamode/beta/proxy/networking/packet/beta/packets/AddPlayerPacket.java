package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPlayerPacket(int id, String name, Vec3i position, byte yaw, byte pitch, short carriedItem) implements RecordPacket {
	public static final int MAX_NAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> NAME_CODEC = ByteBufCodecs.stringUtf8(MAX_NAME_LENGTH);
	public static final StreamCodec<ByteBuf, AddPlayerPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.INT,
			AddPlayerPacket::id,
			NAME_CODEC,
			AddPlayerPacket::name,
			Vec3i.CODEC,
			AddPlayerPacket::position,
			ByteBufCodecs.BYTE,
			AddPlayerPacket::yaw,
			ByteBufCodecs.BYTE,
			AddPlayerPacket::pitch,
			ByteBufCodecs.SHORT,
			AddPlayerPacket::carriedItem,
			AddPlayerPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_PLAYER;
	}
}
