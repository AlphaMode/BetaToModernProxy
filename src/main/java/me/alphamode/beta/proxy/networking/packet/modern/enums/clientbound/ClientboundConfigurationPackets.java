package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ClientboundConfigurationPackets implements ModernClientboundPackets {
	COOKIE_REQUEST(0x00, Identifier.vanilla("cookie_request"), PacketState.CONFIGURATION),
	CUSTOM_PAYLOAD(0x01, Identifier.vanilla("custom_payload"), PacketState.CONFIGURATION),
	DISCONNECT(0x02, Identifier.vanilla("disconnect"), PacketState.CONFIGURATION),
	FINISH_CONFIGURATION(0x03, Identifier.vanilla("finish_configuration"), PacketState.CONFIGURATION),
	KEEP_ALIVE(0x04, Identifier.vanilla("keep_alive"), PacketState.CONFIGURATION),
	PING(0x05, Identifier.vanilla("ping"), PacketState.CONFIGURATION),
	RESET_CHAT(0x06, Identifier.vanilla("reset_chat"), PacketState.CONFIGURATION),
	REGISTRY_DATA(0x07, Identifier.vanilla("registry_data"), PacketState.CONFIGURATION),
	RESOURCE_PACK_POP(0x08, Identifier.vanilla("resource_pack_pop"), PacketState.CONFIGURATION),
	RESOURCE_PACK_PUSH(0x09, Identifier.vanilla("resource_pack_push"), PacketState.CONFIGURATION),
	STORE_COOKIE(0x0A, Identifier.vanilla("store_cookie"), PacketState.CONFIGURATION),
	TRANSFER(0x0B, Identifier.vanilla("transfer"), PacketState.CONFIGURATION),
	UPDATE_ENABLED_FEATURES(0x0C, Identifier.vanilla("update_enabled_features"), PacketState.CONFIGURATION),
	UPDATE_TAGS(0x0D, Identifier.vanilla("update_tags"), PacketState.CONFIGURATION),
	SELECT_KNOWN_PACKS(0x0E, Identifier.vanilla("select_known_packs"), PacketState.CONFIGURATION),
	CUSTOM_REPORT_DETAILS(0x0F, Identifier.vanilla("custom_report_details"), PacketState.CONFIGURATION),
	SERVER_LINKS(0x10, Identifier.vanilla("server_links"), PacketState.CONFIGURATION),
	CLEAR_DIALOG(0x11, Identifier.vanilla("clear_dialog"), PacketState.CONFIGURATION),
	SHOW_DIALOG(0x12, Identifier.vanilla("show_dialog"), PacketState.CONFIGURATION),
	CODE_OF_CONDUCT(0x13, Identifier.vanilla("code_of_conduct"), PacketState.CONFIGURATION);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ClientboundConfigurationPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ClientboundConfigurationPackets getPacket(final int id) {
		for (final ClientboundConfigurationPackets packet : ClientboundConfigurationPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
