package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ServerboundPlayPackets implements ModernServerboundPackets {
	ACCEPT_TELEPORTATION(0x00, Identifier.vanilla("accept_teleportation"), PacketState.PLAY),
	BLOCK_ENTITY_TAG_QUERY(0x00, Identifier.vanilla("block_entity_tag_query"), PacketState.PLAY),
	BUNDLE_ITEM_SELECTED(0x00, Identifier.vanilla("bundle_item_selected"), PacketState.PLAY),
	CHANGE_DIFFICULTY(0x00, Identifier.vanilla("change_difficulty"), PacketState.PLAY),
	CHANGE_GAME_MODE(0x00, Identifier.vanilla("change_game_mode"), PacketState.PLAY),
	CHAT_ACK(0x00, Identifier.vanilla("chat_ack"), PacketState.PLAY),
	CHAT_COMMAND(0x00, Identifier.vanilla("chat_command"), PacketState.PLAY),
	CHAT_COMMAND_SIGNED(0x00, Identifier.vanilla("chat_command_signed"), PacketState.PLAY),
	CHAT(0x00, Identifier.vanilla("chat"), PacketState.PLAY),
	CHAT_SESSION_UPDATE(0x00, Identifier.vanilla("chat_session_update"), PacketState.PLAY),
	CHUNK_BATCH_RECEIVED(0x00, Identifier.vanilla("chunk_batch_received"), PacketState.PLAY),
	CLIENT_COMMAND(0x00, Identifier.vanilla("client_command"), PacketState.PLAY),
	CLIENT_TICK_END(0x00, Identifier.vanilla("client_tick_end"), PacketState.PLAY),
	CLIENT_INFORMATION(0x00, Identifier.vanilla("client_information"), PacketState.PLAY),
	COMMAND_SUGGESTION(0x00, Identifier.vanilla("command_suggestion"), PacketState.PLAY),
	CONFIGURATION_ACKNOWLEDGED(0x00, Identifier.vanilla("configuration_acknowledged"), PacketState.PLAY),
	CONTAINER_BUTTON_CLICK(0x00, Identifier.vanilla("container_button_click"), PacketState.PLAY),
	CONTAINER_CLICK(0x00, Identifier.vanilla("container_click"), PacketState.PLAY),
	CONTAINER_CLOSE(0x00, Identifier.vanilla("container_close"), PacketState.PLAY),
	CONTAINER_SLOT_STATE_CHANGED(0x00, Identifier.vanilla("container_slot_state_changed"), PacketState.PLAY),
	COOKIE_RESPONSE(0x00, Identifier.vanilla("cookie_response"), PacketState.PLAY),
	CUSTOM_PAYLOAD(0x00, Identifier.vanilla("custom_payload"), PacketState.PLAY),
	DEBUG_SUBSCRIPTION_REQUEST(0x00, Identifier.vanilla("debug_subscription_request"), PacketState.PLAY),
	EDIT_BOOK(0x00, Identifier.vanilla("edit_book"), PacketState.PLAY),
	ENTITY_TAG_QUERY(0x00, Identifier.vanilla("entity_tag_query"), PacketState.PLAY),
	INTERACT(0x00, Identifier.vanilla("interact"), PacketState.PLAY),
	JIGSAW_GENERATE(0x00, Identifier.vanilla("jigsaw_generate"), PacketState.PLAY),
	KEEP_ALIVE(0x00, Identifier.vanilla("keep_alive"), PacketState.PLAY),
	LOCK_DIFFICULTY(0x00, Identifier.vanilla("lock_difficulty"), PacketState.PLAY),
	MOVE_PLAYER_POS(0x00, Identifier.vanilla("move_player_pos"), PacketState.PLAY),
	MOVE_PLAYER_POS_ROT(0x00, Identifier.vanilla("move_player_pos_rot"), PacketState.PLAY),
	MOVE_PLAYER_STATUS_ONLY(0x00, Identifier.vanilla("move_player_status_only"), PacketState.PLAY),
	MOVE_VEHICLE(0x00, Identifier.vanilla("move_vehicle"), PacketState.PLAY),
	PADDLE_BOAT(0x00, Identifier.vanilla("paddle_boat"), PacketState.PLAY),
	PICK_ITEM_FROM_BLOCK(0x00, Identifier.vanilla("pick_item_from_block"), PacketState.PLAY),
	PICK_ITEM_FROM_ENTITY(0x00, Identifier.vanilla("pick_item_from_entity"), PacketState.PLAY),
	PING_REQUEST(0x00, Identifier.vanilla("ping_request"), PacketState.PLAY),
	PLACE_RECIPE(0x00, Identifier.vanilla("place_recipe"), PacketState.PLAY),
	PLAYER_ABILITIES(0x00, Identifier.vanilla("player_abilities"), PacketState.PLAY),
	PLAYER_ACTION(0x00, Identifier.vanilla("player_action"), PacketState.PLAY),
	PLAYER_COMMAND(0x00, Identifier.vanilla("player_command"), PacketState.PLAY),
	PLAYER_INPUT(0x00, Identifier.vanilla("player_input"), PacketState.PLAY),
	PLAYER_LOADED(0x00, Identifier.vanilla("player_loaded"), PacketState.PLAY),
	PONG(0x00, Identifier.vanilla("pong"), PacketState.PLAY),
	RECIPE_BOOK_CHANGE_SETTINGS(0x00, Identifier.vanilla("recipe_book_change_settings"), PacketState.PLAY),
	RECIPE_BOOK_SEEN_RECIPE(0x00, Identifier.vanilla("recipe_book_seen_recipe"), PacketState.PLAY),
	RENAME_ITEM(0x00, Identifier.vanilla("rename_item"), PacketState.PLAY),
	RESOURCE_PACK(0x00, Identifier.vanilla("resource_pack"), PacketState.PLAY),
	SEEN_ADVANCEMENTS(0x00, Identifier.vanilla("seen_advancements"), PacketState.PLAY),
	SELECT_TRADE(0x00, Identifier.vanilla("select_trade"), PacketState.PLAY),
	SET_BEACON(0x00, Identifier.vanilla("set_beacon"), PacketState.PLAY),
	SET_CARRIED_ITEM(0x00, Identifier.vanilla("set_carried_item"), PacketState.PLAY),
	SET_COMMAND_BLOCK(0x00, Identifier.vanilla("set_command_block"), PacketState.PLAY),
	SET_COMMAND_MINECART(0x00, Identifier.vanilla("set_command_minecart"), PacketState.PLAY),
	SET_CREATIVE_MODE_SLOT(0x00, Identifier.vanilla("set_creative_mode_slot"), PacketState.PLAY),
	SET_JIGSAW_BLOCK(0x00, Identifier.vanilla("set_jigsaw_block"), PacketState.PLAY),
	SET_STRUCTURE_BLOCK(0x00, Identifier.vanilla("set_structure_block"), PacketState.PLAY),
	SET_TEST_BLOCK(0x00, Identifier.vanilla("set_test_block"), PacketState.PLAY),
	SIGN_UPDATE(0x00, Identifier.vanilla("sign_update"), PacketState.PLAY),
	SWING(0x00, Identifier.vanilla("swing"), PacketState.PLAY),
	TELEPORT_TO_ENTITY(0x00, Identifier.vanilla("teleport_to_entity"), PacketState.PLAY),
	TEST_INSTANCE_BLOCK_ACTION(0x00, Identifier.vanilla("test_instance_block_action"), PacketState.PLAY),
	USE_ITEM_ON(0x00, Identifier.vanilla("use_item_on"), PacketState.PLAY),
	USE_ITEM(0x00, Identifier.vanilla("use_item"), PacketState.PLAY),
	CUSTOM_CLICK_ACTION(0x00, Identifier.vanilla("custom_click_action"), PacketState.PLAY);

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

}
