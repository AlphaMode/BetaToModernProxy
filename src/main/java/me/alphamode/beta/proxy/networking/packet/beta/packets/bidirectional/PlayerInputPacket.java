package me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional;

import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerInputPacket(float deltaX, float deltaZ, float yaw, float pitch, boolean jumping,
								boolean shifting) implements BetaPacket {
	public static final StreamCodec<ByteStream, PlayerInputPacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.FLOAT,
			PlayerInputPacket::deltaX,
			CommonStreamCodecs.FLOAT,
			PlayerInputPacket::deltaZ,
			CommonStreamCodecs.FLOAT,
			PlayerInputPacket::yaw,
			CommonStreamCodecs.FLOAT,
			PlayerInputPacket::pitch,
			CommonStreamCodecs.BOOL,
			PlayerInputPacket::jumping,
			CommonStreamCodecs.BOOL,
			PlayerInputPacket::shifting,
			PlayerInputPacket::new
	);

	@Override
	public BetaPacketType getType() {
		return BetaPacketType.PLAYER_INPUT;
	}
}
