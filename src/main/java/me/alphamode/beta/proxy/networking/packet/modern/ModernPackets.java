package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.util.Identifier;
import me.alphamode.beta.proxy.util.Tuple;

import java.util.Map;

public enum ModernPackets implements Packets {
	HANDSHAKING(Map.of(

	)),

	PLAY(Map.of(

	)),

	STATUS(Map.of(

	)),

	LOGIN(Map.of(

	)),

	CONFIGURATION(Map.of(

	));

	private final Map<Integer, Map<PacketDirection, Tuple<Integer, Identifier>>> packetMap;

	ModernPackets(final Map<Integer, Map<PacketDirection, Tuple<Integer, Identifier>>> packetMap) {
		this.packetMap = packetMap;
	}
}
