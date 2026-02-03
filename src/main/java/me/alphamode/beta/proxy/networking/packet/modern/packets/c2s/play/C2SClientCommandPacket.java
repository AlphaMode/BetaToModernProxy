package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SClientCommandPacket(Action action) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SClientCommandPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.javaEnum(Action.class),
			C2SClientCommandPacket::action,
			C2SClientCommandPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CLIENT_COMMAND;
	}

	public enum Action {
		PERFORM_RESPAWN,
		REQUEST_STATS
	}
}
