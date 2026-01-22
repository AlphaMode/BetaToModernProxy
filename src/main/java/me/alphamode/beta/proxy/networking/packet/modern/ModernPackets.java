package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.*;
import me.alphamode.beta.proxy.util.data.Identifier;

public interface ModernPackets extends Packets {
	Identifier getIdentifier();

	PacketState getState();

	static <T extends ModernPackets> T getPacket(final int packetId, final PacketDirection direction, final PacketState state) {
		return switch (direction) {
			case SERVERBOUND -> (T) switch (state) {
				case HANDSHAKING -> ServerboundHandshakingPackets.getPacket(packetId);
				case PLAY -> ServerboundPlayPackets.getPacket(packetId);
				case STATUS -> ServerboundStatusPackets.getPacket(packetId);
				case LOGIN -> ServerboundLoginPackets.getPacket(packetId);
				case CONFIGURATION -> ServerboundConfigurationPackets.getPacket(packetId);
			};

			case CLIENTBOUND -> (T) switch (state) {
				case HANDSHAKING -> throw new RuntimeException("Clientbound handshaking packet not allowed");
				case PLAY -> ClientboundPlayPackets.getPacket(packetId);
				case STATUS -> ClientboundStatusPackets.getPacket(packetId);
				case LOGIN -> ClientboundLoginPackets.getPacket(packetId);
				case CONFIGURATION -> ClientboundConfigurationPackets.getPacket(packetId);
			};
		};
	}
}
