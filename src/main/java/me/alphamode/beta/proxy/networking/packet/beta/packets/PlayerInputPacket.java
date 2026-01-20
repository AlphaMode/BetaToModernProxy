package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.BetaPackets;
import me.alphamode.beta.proxy.util.codec.ByteBufCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record PlayerInputPacket(float deltaX, float deltaZ, float yaw, float pitch, boolean jumping,
								boolean shifting) implements RecordPacket {
	public static final StreamCodec<ByteBuf, PlayerInputPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.FLOAT,
			PlayerInputPacket::deltaX,
			ByteBufCodecs.FLOAT,
			PlayerInputPacket::deltaZ,
			ByteBufCodecs.FLOAT,
			PlayerInputPacket::yaw,
			ByteBufCodecs.FLOAT,
			PlayerInputPacket::pitch,
			ByteBufCodecs.BOOL,
			PlayerInputPacket::jumping,
			ByteBufCodecs.BOOL,
			PlayerInputPacket::shifting,
			PlayerInputPacket::new
	);

	@Override
	public BetaPackets getType() {
		return BetaPackets.PLAYER_INPUT;
	}
}
