package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ServerboundLoginPackets implements ModernServerboundPackets {
	HELLO(0x00, Identifier.vanilla("hello"), PacketState.LOGIN),
	KEY(0x01, Identifier.vanilla("key"), PacketState.LOGIN),
	CUSTOM_QUERY_ANSWER(0x02, Identifier.vanilla("custom_query_answer"), PacketState.LOGIN),
	ACKNOWLEDGED(0x03, Identifier.vanilla("login_acknowledged"), PacketState.LOGIN),
	COOKIE_RESPONSE(0x04, Identifier.vanilla("cookie_response"), PacketState.LOGIN);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ServerboundLoginPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ServerboundLoginPackets getPacket(final int id) {
		for (final ServerboundLoginPackets packet : ServerboundLoginPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
