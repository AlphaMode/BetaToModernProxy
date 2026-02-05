package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import net.lenni0451.mcstructs.core.Identifier;

public enum ServerboundConfigurationPackets implements ModernServerboundPackets {
	CLIENT_INFORMATION(0x00, Identifier.defaultNamespace("client_information"), PacketState.CONFIGURATION),
	COOKIE_RESPONSE(0x01, Identifier.defaultNamespace("client_information"), PacketState.CONFIGURATION),
	CUSTOM_PAYLOAD(0x02, Identifier.defaultNamespace("custom_payload"), PacketState.CONFIGURATION),
	FINISH_CONFIGURATION(0x03, Identifier.defaultNamespace("finish_configuration"), PacketState.CONFIGURATION),
	KEEP_ALIVE(0x04, Identifier.defaultNamespace("keep_alive"), PacketState.CONFIGURATION),
	PONG(0x05, Identifier.defaultNamespace("pong"), PacketState.CONFIGURATION),
	RESOURCE_PACK(0x06, Identifier.defaultNamespace("resource_pack"), PacketState.CONFIGURATION),
	SELECT_KNOWN_PACKS(0x07, Identifier.defaultNamespace("select_known_packs"), PacketState.CONFIGURATION),
	CUSTOM_CLICK_ACTION(0x08, Identifier.defaultNamespace("custom_click_action"), PacketState.CONFIGURATION),
	ACCEPT_CODE_OF_CONDUCT(0x09, Identifier.defaultNamespace("custom_click_action"), PacketState.CONFIGURATION);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ServerboundConfigurationPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ServerboundConfigurationPackets byId(final int id) {
		for (final ServerboundConfigurationPackets packet : ServerboundConfigurationPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
