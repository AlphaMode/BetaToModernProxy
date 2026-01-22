package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import net.lenni0451.mcstructs.core.Identifier;

public enum ServerboundPlayPackets implements ModernServerboundPackets {
	ACCEPT_TELEPORTATION(0x00, Identifier.defaultNamespace("accept_teleportation"), PacketState.PLAY),
	BLOCK_ENTITY_TAG_QUERY(0x01, Identifier.defaultNamespace("block_entity_tag_query"), PacketState.PLAY),
	BUNDLE_ITEM_SELECTED(0x02, Identifier.defaultNamespace("bundle_item_selected"), PacketState.PLAY),
	CHANGE_DIFFICULTY(0x03, Identifier.defaultNamespace("change_difficulty"), PacketState.PLAY),
	CHANGE_GAME_MODE(0x04, Identifier.defaultNamespace("change_game_mode"), PacketState.PLAY),
	CHAT_ACK(0x05, Identifier.defaultNamespace("chat_ack"), PacketState.PLAY),
	CHAT_COMMAND(0x06, Identifier.defaultNamespace("chat_command"), PacketState.PLAY),
	CHAT_COMMAND_SIGNED(0x07, Identifier.defaultNamespace("chat_command_signed"), PacketState.PLAY),
	CHAT(0x08, Identifier.defaultNamespace("chat"), PacketState.PLAY),
	CHAT_SESSION_UPDATE(0x09, Identifier.defaultNamespace("chat_session_update"), PacketState.PLAY),
	CHUNK_BATCH_RECEIVED(0x0A, Identifier.defaultNamespace("chunk_batch_received"), PacketState.PLAY),
	CLIENT_COMMAND(0x0B, Identifier.defaultNamespace("client_command"), PacketState.PLAY),
	CLIENT_TICK_END(0x0C, Identifier.defaultNamespace("client_tick_end"), PacketState.PLAY),
	CLIENT_INFORMATION(0x0D, Identifier.defaultNamespace("client_information"), PacketState.PLAY),
	COMMAND_SUGGESTION(0x0E, Identifier.defaultNamespace("command_suggestion"), PacketState.PLAY),
	CONFIGURATION_ACKNOWLEDGED(0x0F, Identifier.defaultNamespace("configuration_acknowledged"), PacketState.PLAY),
	CONTAINER_BUTTON_CLICK(0x10, Identifier.defaultNamespace("container_button_click"), PacketState.PLAY),
	CONTAINER_CLICK(0x11, Identifier.defaultNamespace("container_click"), PacketState.PLAY),
	CONTAINER_CLOSE(0x12, Identifier.defaultNamespace("container_close"), PacketState.PLAY),
	CONTAINER_SLOT_STATE_CHANGED(0x13, Identifier.defaultNamespace("container_slot_state_changed"), PacketState.PLAY),
	COOKIE_RESPONSE(0x14, Identifier.defaultNamespace("cookie_response"), PacketState.PLAY),
	CUSTOM_PAYLOAD(0x15, Identifier.defaultNamespace("custom_payload"), PacketState.PLAY),
	DEBUG_SUBSCRIPTION_REQUEST(0x16, Identifier.defaultNamespace("debug_subscription_request"), PacketState.PLAY),
	EDIT_BOOK(0x17, Identifier.defaultNamespace("edit_book"), PacketState.PLAY),
	ENTITY_TAG_QUERY(0x18, Identifier.defaultNamespace("entity_tag_query"), PacketState.PLAY),
	INTERACT(0x19, Identifier.defaultNamespace("interact"), PacketState.PLAY),
	JIGSAW_GENERATE(0x1A, Identifier.defaultNamespace("jigsaw_generate"), PacketState.PLAY),
	KEEP_ALIVE(0x1B, Identifier.defaultNamespace("keep_alive"), PacketState.PLAY),
	LOCK_DIFFICULTY(0x1C, Identifier.defaultNamespace("lock_difficulty"), PacketState.PLAY),
	MOVE_PLAYER_POS(0x1D, Identifier.defaultNamespace("move_player_pos"), PacketState.PLAY),
	MOVE_PLAYER_POS_ROT(0x1E, Identifier.defaultNamespace("move_player_pos_rot"), PacketState.PLAY),
	MOVE_PLAYER_ROT(0x1F, Identifier.defaultNamespace("move_player_rot"), PacketState.PLAY),
	MOVE_PLAYER_STATUS_ONLY(0x20, Identifier.defaultNamespace("move_player_status_only"), PacketState.PLAY),
	MOVE_VEHICLE(0x21, Identifier.defaultNamespace("move_vehicle"), PacketState.PLAY),
	PADDLE_BOAT(0x22, Identifier.defaultNamespace("paddle_boat"), PacketState.PLAY),
	PICK_ITEM_FROM_BLOCK(0x23, Identifier.defaultNamespace("pick_item_from_block"), PacketState.PLAY),
	PICK_ITEM_FROM_ENTITY(0x24, Identifier.defaultNamespace("pick_item_from_entity"), PacketState.PLAY),
	PING_REQUEST(0x25, Identifier.defaultNamespace("ping_request"), PacketState.PLAY),
	PLACE_RECIPE(0x26, Identifier.defaultNamespace("place_recipe"), PacketState.PLAY),
	PLAYER_ABILITIES(0x27, Identifier.defaultNamespace("player_abilities"), PacketState.PLAY),
	PLAYER_ACTION(0x28, Identifier.defaultNamespace("player_action"), PacketState.PLAY),
	PLAYER_COMMAND(0x29, Identifier.defaultNamespace("player_command"), PacketState.PLAY),
	PLAYER_INPUT(0x2A, Identifier.defaultNamespace("player_input"), PacketState.PLAY),
	PLAYER_LOADED(0x2B, Identifier.defaultNamespace("player_loaded"), PacketState.PLAY),
	PONG(0x2C, Identifier.defaultNamespace("pong"), PacketState.PLAY),
	RECIPE_BOOK_CHANGE_SETTINGS(0x2D, Identifier.defaultNamespace("recipe_book_change_settings"), PacketState.PLAY),
	RECIPE_BOOK_SEEN_RECIPE(0x2E, Identifier.defaultNamespace("recipe_book_seen_recipe"), PacketState.PLAY),
	RENAME_ITEM(0x2F, Identifier.defaultNamespace("rename_item"), PacketState.PLAY),
	RESOURCE_PACK(0x30, Identifier.defaultNamespace("resource_pack"), PacketState.PLAY),
	SEEN_ADVANCEMENTS(0x31, Identifier.defaultNamespace("seen_advancements"), PacketState.PLAY),
	SELECT_TRADE(0x32, Identifier.defaultNamespace("select_trade"), PacketState.PLAY),
	SET_BEACON(0x33, Identifier.defaultNamespace("set_beacon"), PacketState.PLAY),
	SET_CARRIED_ITEM(0x34, Identifier.defaultNamespace("set_carried_item"), PacketState.PLAY),
	SET_COMMAND_BLOCK(0x35, Identifier.defaultNamespace("set_command_block"), PacketState.PLAY),
	SET_COMMAND_MINECART(0x36, Identifier.defaultNamespace("set_command_minecart"), PacketState.PLAY),
	SET_CREATIVE_MODE_SLOT(0x37, Identifier.defaultNamespace("set_creative_mode_slot"), PacketState.PLAY),
	SET_JIGSAW_BLOCK(0x38, Identifier.defaultNamespace("set_jigsaw_block"), PacketState.PLAY),
	SET_STRUCTURE_BLOCK(0x39, Identifier.defaultNamespace("set_structure_block"), PacketState.PLAY),
	SET_TEST_BLOCK(0x3A, Identifier.defaultNamespace("set_test_block"), PacketState.PLAY),
	SIGN_UPDATE(0x3B, Identifier.defaultNamespace("sign_update"), PacketState.PLAY),
	SWING(0x3C, Identifier.defaultNamespace("swing"), PacketState.PLAY),
	TELEPORT_TO_ENTITY(0x3D, Identifier.defaultNamespace("teleport_to_entity"), PacketState.PLAY),
	TEST_INSTANCE_BLOCK_ACTION(0x3E, Identifier.defaultNamespace("test_instance_block_action"), PacketState.PLAY),
	USE_ITEM_ON(0x3F, Identifier.defaultNamespace("use_item_on"), PacketState.PLAY),
	USE_ITEM(0x40, Identifier.defaultNamespace("use_item"), PacketState.PLAY),
	CUSTOM_CLICK_ACTION(0x41, Identifier.defaultNamespace("custom_click_action"), PacketState.PLAY);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ServerboundPlayPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ServerboundPlayPackets getPacket(final int id) {
		for (final ServerboundPlayPackets packet : ServerboundPlayPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
