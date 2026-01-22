package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SAcceptTeleportationPacket(int id) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SAcceptTeleportationPacket> CODEC = StreamCodec.composite(
			ModernCodecs.VAR_INT,
			C2SAcceptTeleportationPacket::id,
			C2SAcceptTeleportationPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.ACCEPT_TELEPORTATION;
	}
}
