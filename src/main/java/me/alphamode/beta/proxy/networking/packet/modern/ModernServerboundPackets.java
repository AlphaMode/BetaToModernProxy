package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.util.data.Identifier;

public enum ModernServerboundPackets implements ModernPackets {
	// Handshaking
	INTENTION(0x00, Identifier.vanilla("intention"), PacketState.HANDSHAKING),

	// Play

	// Status
	STATUS_REQUEST(0x00, Identifier.vanilla("status_request"), PacketState.STATUS),
	PING_REQUEST(0x01, Identifier.vanilla("ping_request"), PacketState.STATUS),

	// Login
	HELLO(0x00, Identifier.vanilla("hello"), PacketState.LOGIN),
	KEY(0x01, Identifier.vanilla("key"), PacketState.LOGIN),
	CUSTOM_QUERY_ANSWER(0x02, Identifier.vanilla("custom_query_answer"), PacketState.LOGIN),
	LOGIN_ACKNOWLEDGED(0x03, Identifier.vanilla("login_acknowledged"), PacketState.LOGIN),
	LOGIN_COOKIE_RESPONSE(0x04, Identifier.vanilla("cookie_response"), PacketState.LOGIN),

	// Configuration
	CLIENT_INFORMATION(0x00, Identifier.vanilla("client_information"), PacketState.CONFIGURATION),
	CONFIGURATION_COOKIE_RESPONSE(0x01, Identifier.vanilla("client_information"), PacketState.CONFIGURATION),
	CUSTOM_PAYLOAD(0x02, Identifier.vanilla("custom_payload"), PacketState.CONFIGURATION),
	FINISH_CONFIGURATION(0x03, Identifier.vanilla("finish_configuration"), PacketState.CONFIGURATION),
	KEEP_ALIVE(0x04, Identifier.vanilla("keep_alive"), PacketState.CONFIGURATION),
	PONG(0x05, Identifier.vanilla("pong"), PacketState.CONFIGURATION),
	RESOURCE_PACK(0x06, Identifier.vanilla("resource_pack"), PacketState.CONFIGURATION),
	SELECT_KNOWN_PACKS(0x07, Identifier.vanilla("select_known_packs"), PacketState.CONFIGURATION),
	CUSTOM_CLICK_ACTION(0x08, Identifier.vanilla("custom_click_action"), PacketState.CONFIGURATION),
	ACCEPT_CODE_OF_CONDUCT(0x09, Identifier.vanilla("custom_click_action"), PacketState.CONFIGURATION);

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

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public PacketState getState() {
		return this.state;
	}

	public static ModernServerboundPackets getPacket(final int id, final PacketState state) {
		for (final ModernServerboundPackets packet : ModernServerboundPackets.values()) {
			if (packet.getId() == id && packet.state == state) {
				return packet;
			}
		}

		return null;
	}
}
