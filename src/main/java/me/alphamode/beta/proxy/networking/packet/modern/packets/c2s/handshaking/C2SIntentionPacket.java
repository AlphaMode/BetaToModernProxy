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
		STATUS(1),
		LOGIN(2),
		TRANSFER(3);

        public static final StreamCodec<ByteStream, ClientIntent> CODEC = StreamCodec.of((output, value) -> ModernStreamCodecs.VAR_INT.encode(output, value.id()), input -> byId(ModernStreamCodecs.VAR_INT.decode(input)));
        private int id;

        ClientIntent(final int id) {
            this.id = id;
        }

		public int id() {
			return this.id;
		}

        public static ClientIntent byId(final int id) {
            return switch (id) {
                case 1 -> STATUS;
                case 2 -> LOGIN;
                case 3 -> TRANSFER;
                default -> throw new IllegalArgumentException("Unknown connection intent: " + id);
            };
        }
	}
}
