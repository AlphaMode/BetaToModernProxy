package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.util.Identifier;
import me.alphamode.beta.proxy.util.Tuple;

import java.util.List;
import java.util.Map;

public enum ModernPackets implements Packets {
	HANDSHAKING(Map.of(
			PacketDirection.SERVERBOUND, List.of(),
			PacketDirection.CLIENTBOUND, List.of()
	)),

	PLAY(Map.of(
			PacketDirection.SERVERBOUND, List.of(),
			PacketDirection.CLIENTBOUND, List.of()
	)),

	STATUS(Map.of(
			PacketDirection.SERVERBOUND, List.of(),
			PacketDirection.CLIENTBOUND, List.of()
	)),

	LOGIN(Map.of(
			PacketDirection.SERVERBOUND, List.of(),
			PacketDirection.CLIENTBOUND, List.of()
	)),

	CONFIGURATION(Map.of(
			PacketDirection.SERVERBOUND, List.of(),
			PacketDirection.CLIENTBOUND, List.of()
	));

	private final Map<PacketDirection, List<Tuple<Integer, Identifier>>> packets;

	ModernPackets(final Map<PacketDirection, List<Tuple<Integer, Identifier>>> packetMap) {
		this.packets = packetMap;
	}
}
