package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.PositionMoveRotation;
import me.alphamode.beta.proxy.util.data.modern.enums.Relative;

import java.util.Set;

public record S2CTeleportEntityPacket(int entityId, PositionMoveRotation change, Set<Relative> relatives,
									  boolean onGround) implements S2CPlayPacket {
	public static final StreamCodec<ByteStream, S2CTeleportEntityPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CTeleportEntityPacket::entityId,
			PositionMoveRotation.CODEC,
			S2CTeleportEntityPacket::change,
			Relative.SET_STREAM_CODEC,
			S2CTeleportEntityPacket::relatives,
			CommonStreamCodecs.BOOL,
			S2CTeleportEntityPacket::onGround,
			S2CTeleportEntityPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.TELEPORT_ENTITY;
	}
}
