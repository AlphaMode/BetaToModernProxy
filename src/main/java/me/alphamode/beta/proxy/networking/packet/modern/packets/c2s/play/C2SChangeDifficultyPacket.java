package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.Difficulty;

public record C2SChangeDifficultyPacket(Difficulty difficulty) implements C2SPlayPacket {
	public static final StreamCodec<ByteBuf, C2SChangeDifficultyPacket> CODEC = StreamCodec.composite(
			Difficulty.CODEC,
			C2SChangeDifficultyPacket::difficulty,
			C2SChangeDifficultyPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CHANGE_DIFFICULTY;
	}
}
