package me.alphamode.beta.proxy.networking.packet.modern;

public enum PacketState {
	HANDSHAKING,
	PLAY,
	STATUS,
	LOGIN,
	CONFIGURATION;

	public static PacketState fromId(final int id) {
		return values()[id];
	}
}
