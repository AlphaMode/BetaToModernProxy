package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.PositionMoveRotation;
import me.alphamode.beta.proxy.util.data.modern.enums.Relative;

import java.util.Set;

public record S2CPlayerPositionPacket(int id, PositionMoveRotation change,
									  Set<Relative> relatives) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CPlayerPositionPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CPlayerPositionPacket::id,
			PositionMoveRotation.CODEC,
			S2CPlayerPositionPacket::change,
			Relative.SET_STREAM_CODEC,
			S2CPlayerPositionPacket::relatives,
			S2CPlayerPositionPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.PLAYER_POSITION;
	}
}
