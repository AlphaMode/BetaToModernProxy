package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ClientboundStatusPackets implements ModernClientboundPackets {
	STATUS_RESPONSE(0x00, Identifier.vanilla("status_response"), PacketState.STATUS),
	PONG_RESPONSE(0x01, Identifier.vanilla("pong_response"), PacketState.STATUS);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ClientboundStatusPackets(final int id, final Identifier identifier, final PacketState state) {
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
}
