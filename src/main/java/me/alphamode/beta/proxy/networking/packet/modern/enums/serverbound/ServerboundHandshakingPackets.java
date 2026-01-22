package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ServerboundHandshakingPackets implements ModernServerboundPackets {
	INTENTION(0x00, Identifier.vanilla("intention"), PacketState.HANDSHAKING),
	LEGACY_SERVER_LIST_PING(0xFE, Identifier.vanilla("legacy_server_list_ping"), PacketState.HANDSHAKING);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ServerboundHandshakingPackets(final int id, final Identifier identifier, final PacketState state) {
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
