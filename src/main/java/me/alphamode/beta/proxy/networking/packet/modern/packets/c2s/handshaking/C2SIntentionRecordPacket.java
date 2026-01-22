package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.util.codec.BasicCodecs;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SIntentionRecordPacket(int protocolVersion, String hostName, short port, ClientIntent intention) implements C2SHandshakingPacket {
	public static final StreamCodec<ByteBuf, C2SIntentionRecordPacket> CODEC = StreamCodec.composite(
			ModernCodecs.VAR_INT,
			C2SIntentionRecordPacket::protocolVersion,
			ModernCodecs.stringUtf8(),
			C2SIntentionRecordPacket::hostName,
			BasicCodecs.SHORT,
			C2SIntentionRecordPacket::port,
			ClientIntent.CODEC,
			C2SIntentionRecordPacket::intention,
			C2SIntentionRecordPacket::new
	);

	@Override
	public ServerboundHandshakingPackets getType() {
		return ServerboundHandshakingPackets.INTENTION;
	}

	public enum ClientIntent {
		STATUS,
		LOGIN,
		TRANSFER;

		private static final int STATUS_ID = 1;
		private static final int LOGIN_ID = 2;
		private static final int TRANSFER_ID = 3;

		public static final StreamCodec<ByteBuf, ClientIntent> CODEC = StreamCodec.of((output, value) -> ModernCodecs.VAR_INT.encode(output, value.id()), input -> byId(ModernCodecs.VAR_INT.decode(input)));

		public static ClientIntent byId(final int id) {
			return switch (id) {
				case STATUS_ID -> STATUS;
				case LOGIN_ID -> LOGIN;
				case TRANSFER_ID -> TRANSFER;
				default -> throw new IllegalArgumentException("Unknown connection intent: " + id);
			};
		}

		public int id() {
			return switch (this) {
				case STATUS -> STATUS_ID;
				case LOGIN -> LOGIN_ID;
				case TRANSFER -> TRANSFER_ID;
			};
		}
	}
}
