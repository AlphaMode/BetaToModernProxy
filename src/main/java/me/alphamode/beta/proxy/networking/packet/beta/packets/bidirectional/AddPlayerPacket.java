package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.BetaCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.Vec3i;

public record AddPlayerPacket(int id, String name, Vec3i position, byte yaw, byte pitch,
							  short carriedItem) implements BetaRecordPacket {
	public static final int MAX_NAME_LENGTH = 16;
	public static final StreamCodec<ByteBuf, String> NAME_CODEC = BetaCodecs.stringUtf8(MAX_NAME_LENGTH);
	public static final StreamCodec<ByteBuf, AddPlayerPacket> CODEC = StreamCodec.composite(
			BasicCodecs.INT,
			AddPlayerPacket::id,
			NAME_CODEC,
			AddPlayerPacket::name,
			Vec3i.CODEC,
			AddPlayerPacket::position,
			BasicCodecs.BYTE,
			AddPlayerPacket::yaw,
			BasicCodecs.BYTE,
			AddPlayerPacket::pitch,
			BasicCodecs.SHORT,
			AddPlayerPacket::carriedItem,
			AddPlayerPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.ADD_PLAYER;
	}
}
