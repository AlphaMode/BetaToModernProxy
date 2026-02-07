package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record C2SIntentionPacket(int protocolVersion, String hostName, short port,
								 ClientIntent intention) implements C2SHandshakingPacket {
	public static final int MAX_HOSTNAME_LENGTH = 255;
	public static final StreamCodec<ByteStream, String> HOSTNAME_CODEC = ModernStreamCodecs.stringUtf8(MAX_HOSTNAME_LENGTH);
	public static final StreamCodec<ByteStream, C2SIntentionPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.VAR_INT,
			C2SIntentionPacket::protocolVersion,
			HOSTNAME_CODEC,
			C2SIntentionPacket::hostName,
			CommonStreamCodecs.SHORT,
			C2SIntentionPacket::port,
			ClientIntent.CODEC,
			C2SIntentionPacket::intention,
			C2SIntentionPacket::new
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

		public static final StreamCodec<ByteStream, ClientIntent> CODEC = StreamCodec.of((output, value) -> ModernStreamCodecs.VAR_INT.encode(output, value.id()), input -> byId(ModernStreamCodecs.VAR_INT.decode(input)));

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
