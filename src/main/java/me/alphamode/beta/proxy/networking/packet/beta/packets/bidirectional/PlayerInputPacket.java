package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerInputPacket(float deltaX, float deltaZ, float yaw, float pitch, boolean jumping,
								boolean shifting) implements BetaPacket {
	public static final StreamCodec<ByteBuf, PlayerInputPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.FLOAT,
			PlayerInputPacket::deltaX,
			BasicStreamCodecs.FLOAT,
			PlayerInputPacket::deltaZ,
			BasicStreamCodecs.FLOAT,
			PlayerInputPacket::yaw,
			BasicStreamCodecs.FLOAT,
			PlayerInputPacket::pitch,
			BasicStreamCodecs.BOOL,
			PlayerInputPacket::jumping,
			BasicStreamCodecs.BOOL,
			PlayerInputPacket::shifting,
			PlayerInputPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_INPUT;
	}
}
