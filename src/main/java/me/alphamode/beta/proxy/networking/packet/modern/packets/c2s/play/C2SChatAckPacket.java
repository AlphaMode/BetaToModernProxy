package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SChatAckPacket(int offset) implements C2SPlayPacket {
	public static final StreamCodec<ByteStream, C2SChatAckPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SChatAckPacket::offset,
			C2SChatAckPacket::new
	);

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CHAT_ACK;
	}
}
