package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.util.data.Identifier;

public interface ModernPackets extends Packets {
	Identifier getIdentifier();

	PacketState getState();

	static <T extends ModernPackets> T getPacket(final int id, final PacketDirection direction, final PacketState state) {
		return switch (direction) {
			case SERVERBOUND -> switch (state) {
				case HANDSHAKING -> null;
				case PLAY -> null;
				case STATUS -> null;
				case LOGIN -> null;
				case CONFIGURATION -> null;
			};

			case CLIENTBOUND -> switch (state) {
				case HANDSHAKING -> null;
				case PLAY -> null;
				case STATUS -> null;
				case LOGIN -> null;
				case CONFIGURATION -> null;
			};
		};
	}
}
