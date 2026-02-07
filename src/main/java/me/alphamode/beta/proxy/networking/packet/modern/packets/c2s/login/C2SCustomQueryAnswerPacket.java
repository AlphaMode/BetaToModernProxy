package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SCustomQueryAnswerPacket(int transactionId, byte[] data) implements C2SLoginPacket {
	public static final StreamCodec<ByteStream, C2SCustomQueryAnswerPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SCustomQueryAnswerPacket::transactionId,
			ModernStreamCodecs.BYTE_ARRAY,
			C2SCustomQueryAnswerPacket::data,
			C2SCustomQueryAnswerPacket::new
	);

	@Override
	public ServerboundLoginPackets getType() {
		return ServerboundLoginPackets.CUSTOM_QUERY_ANSWER;
	}
}
