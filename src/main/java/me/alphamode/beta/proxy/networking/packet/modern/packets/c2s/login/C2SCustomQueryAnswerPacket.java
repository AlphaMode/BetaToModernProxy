package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundLoginPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SCustomQueryAnswerPacket(int transactionId, byte[] data) implements C2SLoginPacket {
	public static final StreamCodec<ByteBuf, C2SCustomQueryAnswerPacket> CODEC = StreamCodec.composite(
			ModernCodecs.VAR_INT,
			C2SCustomQueryAnswerPacket::transactionId,
			ModernCodecs.BYTE_ARRAY,
			C2SCustomQueryAnswerPacket::data,
			C2SCustomQueryAnswerPacket::new
	);

	@Override
	public ServerboundLoginPackets getType() {
		return ServerboundLoginPackets.CUSTOM_QUERY_ANSWER;
	}
}
