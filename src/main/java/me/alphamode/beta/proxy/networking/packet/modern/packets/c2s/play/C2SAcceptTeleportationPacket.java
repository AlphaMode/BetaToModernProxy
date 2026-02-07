package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SAcceptTeleportationPacket(int id) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SAcceptTeleportationPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SAcceptTeleportationPacket::id,
			C2SAcceptTeleportationPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.ACCEPT_TELEPORTATION;
	}
}
