package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import net.lenni0451.mcstructs.core.Identifier;

public enum ClientboundConfigurationPackets implements ModernClientboundPackets {
	COOKIE_REQUEST(0x00, Identifier.defaultNamespace("cookie_request"), PacketState.CONFIGURATION),
	CUSTOM_PAYLOAD(0x01, Identifier.defaultNamespace("custom_payload"), PacketState.CONFIGURATION),
	DISCONNECT(0x02, Identifier.defaultNamespace("disconnect"), PacketState.CONFIGURATION),
	FINISH_CONFIGURATION(0x03, Identifier.defaultNamespace("finish_configuration"), PacketState.CONFIGURATION),
	KEEP_ALIVE(0x04, Identifier.defaultNamespace("keep_alive"), PacketState.CONFIGURATION),
	PING(0x05, Identifier.defaultNamespace("ping"), PacketState.CONFIGURATION),
	RESET_CHAT(0x06, Identifier.defaultNamespace("reset_chat"), PacketState.CONFIGURATION),
	REGISTRY_DATA(0x07, Identifier.defaultNamespace("registry_data"), PacketState.CONFIGURATION),
	RESOURCE_PACK_POP(0x08, Identifier.defaultNamespace("resource_pack_pop"), PacketState.CONFIGURATION),
	RESOURCE_PACK_PUSH(0x09, Identifier.defaultNamespace("resource_pack_push"), PacketState.CONFIGURATION),
	STORE_COOKIE(0x0A, Identifier.defaultNamespace("store_cookie"), PacketState.CONFIGURATION),
	TRANSFER(0x0B, Identifier.defaultNamespace("transfer"), PacketState.CONFIGURATION),
	UPDATE_ENABLED_FEATURES(0x0C, Identifier.defaultNamespace("update_enabled_features"), PacketState.CONFIGURATION),
	UPDATE_TAGS(0x0D, Identifier.defaultNamespace("update_tags"), PacketState.CONFIGURATION),
	SELECT_KNOWN_PACKS(0x0E, Identifier.defaultNamespace("select_known_packs"), PacketState.CONFIGURATION),
	CUSTOM_REPORT_DETAILS(0x0F, Identifier.defaultNamespace("custom_report_details"), PacketState.CONFIGURATION),
	SERVER_LINKS(0x10, Identifier.defaultNamespace("server_links"), PacketState.CONFIGURATION),
	CLEAR_DIALOG(0x11, Identifier.defaultNamespace("clear_dialog"), PacketState.CONFIGURATION),
	SHOW_DIALOG(0x12, Identifier.defaultNamespace("show_dialog"), PacketState.CONFIGURATION),
	CODE_OF_CONDUCT(0x13, Identifier.defaultNamespace("code_of_conduct"), PacketState.CONFIGURATION);

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
