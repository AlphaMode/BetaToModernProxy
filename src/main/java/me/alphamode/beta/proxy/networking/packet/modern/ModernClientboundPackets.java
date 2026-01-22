package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.util.data.Identifier;

public enum ModernClientboundPackets implements ModernPackets {
	// Handshaking

	// Play

	// Status
	STATUS_RESPONSE(0x00, Identifier.vanilla("status_response"), PacketState.STATUS),
	PONG_RESPONSE(0x01, Identifier.vanilla("pong_response"), PacketState.STATUS);

	// Login

	// Configuration

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ModernClientboundPackets(final int id, final Identifier identifier, final PacketState state) {
		this.id = id;
		this.identifier = identifier;
		this.state = state;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public PacketState getState() {
		return this.state;
	}

	public static ModernClientboundPackets getPacket(final int id) {
		for (final ModernClientboundPackets packet : ModernClientboundPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
