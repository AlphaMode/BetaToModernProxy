package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;

import java.util.HashMap;
import java.util.Map;

public enum ModernPackets implements Packets {
	HANDSHAKING(
			new HashMap<>()
	),
	PLAY(
			new HashMap<>()
	),
	STATUS(
			new HashMap<>()
	),
	LOGIN(
			new HashMap<>()
	),
	CONFIGURATION(
			new HashMap<>()
	);

	private final Map<RecordPacket<?>, PacketDirection> packetMap;

	ModernPackets(final Map<RecordPacket<?>, PacketDirection> packetMap) {
		this.packetMap = packetMap;
	}
}
