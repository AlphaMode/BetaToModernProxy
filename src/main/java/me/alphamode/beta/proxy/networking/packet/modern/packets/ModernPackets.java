package me.alphamode.beta.proxy.networking.packet.modern.packets;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.*;
import net.lenni0451.mcstructs.core.Identifier;

public interface ModernPackets extends Packets {
	Identifier getIdentifier();

	PacketState getState();

	static <T extends ModernPackets> T getPacket(final int packetId, final PacketDirection direction, final PacketState state) {
		return switch (direction) {
			case SERVERBOUND -> (T) switch (state) {
				case HANDSHAKING -> ServerboundHandshakingPackets.byId(packetId);
				case PLAY -> ServerboundPlayPackets.byId(packetId);
				case STATUS -> ServerboundStatusPackets.byId(packetId);
				case LOGIN -> ServerboundLoginPackets.byId(packetId);
				case CONFIGURATION -> ServerboundConfigurationPackets.byId(packetId);
			};

			case CLIENTBOUND -> (T) switch (state) {
				case HANDSHAKING -> throw new RuntimeException("Clientbound handshaking packet not allowed");
				case PLAY -> ClientboundPlayPackets.byId(packetId);
				case STATUS -> ClientboundStatusPackets.byId(packetId);
				case LOGIN -> ClientboundLoginPackets.byId(packetId);
				case CONFIGURATION -> ClientboundConfigurationPackets.byId(packetId);
			};
		};
	}
}
