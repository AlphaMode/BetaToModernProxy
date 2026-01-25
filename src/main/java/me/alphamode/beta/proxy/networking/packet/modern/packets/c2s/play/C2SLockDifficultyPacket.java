package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SLockDifficultyPacket(boolean locked) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SLockDifficultyPacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.BOOL,
			C2SLockDifficultyPacket::locked,
			C2SLockDifficultyPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.LOCK_DIFFICULTY;
	}
}
