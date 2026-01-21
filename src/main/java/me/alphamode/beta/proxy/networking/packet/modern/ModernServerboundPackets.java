package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.util.Identifier;

public enum ModernServerboundPackets implements Packets {
	// Handshaking
	INTENTION(0x00, Identifier.vanilla("intention"), PacketState.HANDSHAKING),

	// Play

	// Status
	STATUS_REQUEST(0x00, Identifier.vanilla("status_request"), PacketState.STATUS),
	PING_REQUEST(0x01, Identifier.vanilla("ping_request"), PacketState.STATUS);

	// Login

	// Configuration

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ModernServerboundPackets(final int id, final Identifier identifier, final PacketState state) {
		this.id = id;
		this.identifier = identifier;
		this.state = state;
	}

	@Override
	public int getId() {
		return this.id;
	}

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public PacketState getState() {
		return this.state;
	}
}
