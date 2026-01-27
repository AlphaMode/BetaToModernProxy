package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.PositionMoveRotation;
import me.alphamode.beta.proxy.util.data.modern.enums.Relative;

import java.util.Set;

public record S2CTeleportEntityPacket(int id, PositionMoveRotation change, Set<Relative> relatives,
									  boolean onGround) implements S2CPlayPacket {
	public static final StreamCodec<ByteBuf, S2CTeleportEntityPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			S2CTeleportEntityPacket::id,
			PositionMoveRotation.CODEC,
			S2CTeleportEntityPacket::change,
			Relative.SET_STREAM_CODEC,
			S2CTeleportEntityPacket::relatives,
			BasicStreamCodecs.BOOL,
			S2CTeleportEntityPacket::onGround,
			S2CTeleportEntityPacket::new
	);

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.TELEPORT_ENTITY;
	}
}
