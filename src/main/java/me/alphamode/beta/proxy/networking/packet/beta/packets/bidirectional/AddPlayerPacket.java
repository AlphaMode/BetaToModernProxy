package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.BetaStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPlayerPacket(int id, String name, Vec3i position, byte yaw, byte pitch,
							  short carriedItem) implements BetaPacket {
	public static final int MAX_NAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> NAME_CODEC = BetaStreamCodecs.stringUtf8(MAX_NAME_LENGTH);
	public static final StreamCodec<ByteBuf, AddPlayerPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.INT,
			AddPlayerPacket::id,
			NAME_CODEC,
			AddPlayerPacket::name,
			Vec3i.CODEC,
			AddPlayerPacket::position,
			BasicStreamCodecs.BYTE,
			AddPlayerPacket::yaw,
			BasicStreamCodecs.BYTE,
			AddPlayerPacket::pitch,
			BasicStreamCodecs.SHORT,
			AddPlayerPacket::carriedItem,
			AddPlayerPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_PLAYER;
	}
}
