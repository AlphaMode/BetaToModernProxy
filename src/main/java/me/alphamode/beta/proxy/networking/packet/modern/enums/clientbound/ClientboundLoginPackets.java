package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ClientboundLoginPackets implements ModernClientboundPackets {
	LOGIN_DISCONNECT(0x00, Identifier.vanilla("login_disconnect"), PacketState.LOGIN),
	HELLO(0x01, Identifier.vanilla("hello"), PacketState.LOGIN),
	LOGIN_FINISHED(0x02, Identifier.vanilla("login_finished"), PacketState.LOGIN),
	LOGIN_COMPRESSION(0x03, Identifier.vanilla("login_compression"), PacketState.LOGIN),
	CUSTOM_QUERY(0x04, Identifier.vanilla("custom_query"), PacketState.LOGIN),
	COOKIE_REQUEST(0x05, Identifier.vanilla("cookie_request"), PacketState.LOGIN);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ClientboundLoginPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ClientboundLoginPackets getPacket(final int id) {
		for (final ClientboundLoginPackets packet : ClientboundLoginPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
