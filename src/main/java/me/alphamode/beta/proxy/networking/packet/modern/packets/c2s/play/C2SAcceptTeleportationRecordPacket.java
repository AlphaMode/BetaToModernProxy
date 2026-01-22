package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SAcceptTeleportationRecordPacket(int id) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SAcceptTeleportationRecordPacket> CODEC = StreamCodec.composite(
			ModernCodecs.VAR_INT,
			C2SAcceptTeleportationRecordPacket::id,
			C2SAcceptTeleportationRecordPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.ACCEPT_TELEPORTATION;
	}
}
