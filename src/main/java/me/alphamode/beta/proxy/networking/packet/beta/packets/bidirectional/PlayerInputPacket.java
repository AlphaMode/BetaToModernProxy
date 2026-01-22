package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerInputPacket(float deltaX, float deltaZ, float yaw, float pitch, boolean jumping, boolean shifting) implements BetaRecordPacket {
	public static final StreamCodec<ByteBuf, PlayerInputPacket> CODEC = StreamCodec.composite(
			BasicCodecs.FLOAT,
			PlayerInputPacket::deltaX,
			BasicCodecs.FLOAT,
			PlayerInputPacket::deltaZ,
			BasicCodecs.FLOAT,
			PlayerInputPacket::yaw,
			BasicCodecs.FLOAT,
			PlayerInputPacket::pitch,
			BasicCodecs.BOOL,
			PlayerInputPacket::jumping,
			BasicCodecs.BOOL,
			PlayerInputPacket::shifting,
			PlayerInputPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_INPUT;
	}
}
