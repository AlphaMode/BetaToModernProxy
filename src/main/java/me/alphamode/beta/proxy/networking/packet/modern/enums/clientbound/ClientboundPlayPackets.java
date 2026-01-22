package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ClientboundPlayPackets implements ModernClientboundPackets {
	BUNDLE_DELIMITER(0x00, Identifier.vanilla("bundle_delimiter"), PacketState.PLAY),
	ADD_ENTITY(0x01, Identifier.vanilla("add_entity"), PacketState.PLAY),
	ANIMATE(0x02, Identifier.vanilla("animate"), PacketState.PLAY),
	AWARD_STATS(0x03, Identifier.vanilla("award_stats"), PacketState.PLAY),
	BLOCK_CHANGED_ACK(0x04, Identifier.vanilla("block_changed_ack"), PacketState.PLAY),
	BLOCK_DESTRUCTION(0x05, Identifier.vanilla("block_destruction"), PacketState.PLAY),
	BLOCK_ENTITY_DATA(0x06, Identifier.vanilla("block_entity_data"), PacketState.PLAY),
	BLOCK_EVENT(0x07, Identifier.vanilla("block_event"), PacketState.PLAY),
	BLOCK_UPDATE(0x08, Identifier.vanilla("block_update"), PacketState.PLAY),
	BOSS_EVENT(0x09, Identifier.vanilla("boss_event"), PacketState.PLAY),
	CHANGE_DIFFICULTY(0x0A, Identifier.vanilla("change_difficulty"), PacketState.PLAY),
	CHUNK_BATCH_FINISHED(0x0B, Identifier.vanilla("chunk_batch_finished"), PacketState.PLAY),
	CHUNK_BATCH_START(0x0C, Identifier.vanilla("chunk_batch_start"), PacketState.PLAY),
	CHUNKS_BIOMES(0x0D, Identifier.vanilla("chunks_biomes"), PacketState.PLAY),
	CLEAR_TITLES(0x0E, Identifier.vanilla("clear_titles"), PacketState.PLAY),
	COMMAND_SUGGESTIONS(0x0F, Identifier.vanilla("command_suggestions"), PacketState.PLAY),
	COMMANDS(0x10, Identifier.vanilla("commands"), PacketState.PLAY),
	CONTAINER_CLOSE(0x11, Identifier.vanilla("container_close"), PacketState.PLAY),
	CONTAINER_SET_CONTENT(0x12, Identifier.vanilla("container_set_content"), PacketState.PLAY),
	CONTAINER_SET_DATA(0x13, Identifier.vanilla("container_set_data"), PacketState.PLAY),
	CONTAINER_SET_SLOT(0x14, Identifier.vanilla("container_set_slot"), PacketState.PLAY),
	COOKIE_REQUEST(0x15, Identifier.vanilla("cookie_request"), PacketState.PLAY),
	COOLDOWN(0x16, Identifier.vanilla("cooldown"), PacketState.PLAY),
	CUSTOM_CHAT_COMPLETIONS(0x17, Identifier.vanilla("custom_chat_completions"), PacketState.PLAY),
	CUSTOM_PAYLOAD(0x18, Identifier.vanilla("custom_payload"), PacketState.PLAY),
	DAMAGE_EVENT(0x19, Identifier.vanilla("damage_event"), PacketState.PLAY),
	DEBUG_BLOCK_VALUE(0x1A, Identifier.vanilla("debug/block_value"), PacketState.PLAY),
	DEBUG_CHUNK_VALUE(0x1B, Identifier.vanilla("debug/chunk_value"), PacketState.PLAY),
	DEBUG_ENTITY_VALUE(0x1C, Identifier.vanilla("debug/entity_value"), PacketState.PLAY),
	DEBUG_EVENT(0x1D, Identifier.vanilla("debug/event"), PacketState.PLAY),
	DEBUG_SAMPLE(0x1E, Identifier.vanilla("debug_sample"), PacketState.PLAY),
	DELETE_CHAT(0x1F, Identifier.vanilla("delete_chat"), PacketState.PLAY),
	DISCONNECT(0x20, Identifier.vanilla("disconnect"), PacketState.PLAY),
	DISGUISED_CHAT(0x21, Identifier.vanilla("disguised_chat"), PacketState.PLAY),
	ENTITY_EVENT(0x22, Identifier.vanilla("entity_event"), PacketState.PLAY),
	ENTITY_POSITION_SYNC(0x23, Identifier.vanilla("entity_position_sync"), PacketState.PLAY),
	EXPLODE(0x24, Identifier.vanilla("explode"), PacketState.PLAY),
	FORGET_LEVEL_CHUNK(0x25, Identifier.vanilla("forget_level_chunk"), PacketState.PLAY),
	GAME_EVENT(0x26, Identifier.vanilla("game_event"), PacketState.PLAY),
	GAME_TEST_HIGHLIGHT_POS(0x27, Identifier.vanilla("game_test_highlight_pos"), PacketState.PLAY),
	MOUNT_SCREEN_OPEN(0x28, Identifier.vanilla("mount_screen_open"), PacketState.PLAY),
	HURT_ANIMATION(0x29, Identifier.vanilla("hurt_animation"), PacketState.PLAY),
	INITIALIZE_BORDER(0x2A, Identifier.vanilla("initialize_border"), PacketState.PLAY),
	KEEP_ALIVE(0x2B, Identifier.vanilla("keep_alive"), PacketState.PLAY),
	LEVEL_CHUNK_WITH_LIGHT(0x2C, Identifier.vanilla("level_chunk_with_light"), PacketState.PLAY),
	LEVEL_EVENT(0x2D, Identifier.vanilla("level_event"), PacketState.PLAY),
	LEVEL_PARTICLES(0x2E, Identifier.vanilla("level_particles"), PacketState.PLAY),
	LIGHT_UPDATE(0x2F, Identifier.vanilla("light_update"), PacketState.PLAY),
	LOGIN(0x30, Identifier.vanilla("login"), PacketState.PLAY),
	MAP_ITEM_DATA(0x31, Identifier.vanilla("map_item_data"), PacketState.PLAY),
	MERCHANT_OFFERS(0x32, Identifier.vanilla("merchant_offers"), PacketState.PLAY),
	MOVE_ENTITY_POS(0x33, Identifier.vanilla("move_entity_pos"), PacketState.PLAY),
	MOVE_ENTITY_POS_ROT(0x34, Identifier.vanilla("move_entity_pos_rot"), PacketState.PLAY),
	MOVE_MINECART_ALONG_TRACK(0x35, Identifier.vanilla("move_minecart_along_track"), PacketState.PLAY),
	MOVE_ENTITY_ROT(0x36, Identifier.vanilla("move_entity_rot"), PacketState.PLAY),
	MOVE_VEHICLE(0x37, Identifier.vanilla("move_vehicle"), PacketState.PLAY),
	OPEN_BOOK(0x38, Identifier.vanilla("open_book"), PacketState.PLAY),
	OPEN_SCREEN(0x39, Identifier.vanilla("open_screen"), PacketState.PLAY),
	OPEN_SIGN_EDITOR(0x3A, Identifier.vanilla("open_sign_editor"), PacketState.PLAY),
	PING(0x3B, Identifier.vanilla("ping"), PacketState.PLAY),
	PONG_RESPONSE(0x3C, Identifier.vanilla("pong_response"), PacketState.PLAY),
	PLACE_GHOST_RECIPE(0x3D, Identifier.vanilla("place_ghost_recipe"), PacketState.PLAY),
	PLAYER_ABILITIES(0x3E, Identifier.vanilla("player_abilities"), PacketState.PLAY),
	PLAYER_CHAT(0x3F, Identifier.vanilla("player_chat"), PacketState.PLAY),
	PLAYER_COMBAT_END(0x40, Identifier.vanilla("player_combat_end"), PacketState.PLAY),
	PLAYER_COMBAT_ENTER(0x41, Identifier.vanilla("player_combat_enter"), PacketState.PLAY),
	PLAYER_COMBAT_KILL(0x42, Identifier.vanilla("player_combat_kill"), PacketState.PLAY),
	PLAYER_INFO_REMOVE(0x43, Identifier.vanilla("player_info_remove"), PacketState.PLAY),
	PLAYER_INFO_UPDATE(0x44, Identifier.vanilla("player_info_update"), PacketState.PLAY),
	PLAYER_LOOK_AT(0x45, Identifier.vanilla("player_look_at"), PacketState.PLAY),
	PLAYER_POSITION(0x46, Identifier.vanilla("player_position"), PacketState.PLAY),
	PLAYER_ROTATION(0x47, Identifier.vanilla("player_rotation"), PacketState.PLAY),
	RECIPE_BOOK_ADD(0x48, Identifier.vanilla("recipe_book_add"), PacketState.PLAY),
	RECIPE_BOOK_REMOVE(0x49, Identifier.vanilla("recipe_book_remove"), PacketState.PLAY),
	RECIPE_BOOK_SETTINGS(0x4A, Identifier.vanilla("recipe_book_settings"), PacketState.PLAY),
	REMOVE_ENTITIES(0x4B, Identifier.vanilla("remove_entities"), PacketState.PLAY),
	REMOVE_MOB_EFFECT(0x4C, Identifier.vanilla("remove_mob_effect"), PacketState.PLAY),
	RESET_SCORE(0x4D, Identifier.vanilla("reset_score"), PacketState.PLAY),
	RESOURCE_PACK_POP(0x4E, Identifier.vanilla("resource_pack_pop"), PacketState.PLAY),
	RESOURCE_PACK_PUSH(0x4F, Identifier.vanilla("resource_pack_push"), PacketState.PLAY),
	RESPAWN(0x50, Identifier.vanilla("respawn"), PacketState.PLAY),
	ROTATE_HEAD(0x51, Identifier.vanilla("rotate_head"), PacketState.PLAY),
	SECTION_BLOCKS_UPDATE(0x52, Identifier.vanilla("section_blocks_update"), PacketState.PLAY),
	SELECT_ADVANCEMENTS_TAB(0x53, Identifier.vanilla("select_advancements_tab"), PacketState.PLAY),
	SERVER_DATA(0x54, Identifier.vanilla("server_data"), PacketState.PLAY),
	SET_ACTION_BAR_TEXT(0x55, Identifier.vanilla("set_action_bar_text"), PacketState.PLAY),
	SET_BORDER_CENTER(0x56, Identifier.vanilla("set_border_center"), PacketState.PLAY),
	SET_BORDER_LERP_SIZE(0x57, Identifier.vanilla("set_border_lerp_size"), PacketState.PLAY),
	SET_BORDER_SIZE(0x58, Identifier.vanilla("set_border_size"), PacketState.PLAY),
	SET_BORDER_WARNING_DELAY(0x59, Identifier.vanilla("set_border_warning_delay"), PacketState.PLAY),
	SET_BORDER_WARNING_DISTANCE(0x5A, Identifier.vanilla("set_border_warning_distance"), PacketState.PLAY),
	SET_CAMERA(0x5B, Identifier.vanilla("set_camera"), PacketState.PLAY),
	SET_CHUNK_CACHE_CENTER(0x5C, Identifier.vanilla("set_chunk_cache_center"), PacketState.PLAY),
	SET_CHUNK_CACHE_RADIUS(0x5D, Identifier.vanilla("set_chunk_cache_radius"), PacketState.PLAY),
	SET_CURSOR_ITEM(0x5E, Identifier.vanilla("set_cursor_item"), PacketState.PLAY),
	SET_DEFAULT_SPAWN_POSITION(0x5F, Identifier.vanilla("set_default_spawn_position"), PacketState.PLAY),
	SET_DISPLAY_OBJECTIVE(0x60, Identifier.vanilla("set_display_objective"), PacketState.PLAY),
	SET_ENTITY_DATA(0x61, Identifier.vanilla("set_entity_data"), PacketState.PLAY),
	SET_ENTITY_LINK(0x62, Identifier.vanilla("set_entity_link"), PacketState.PLAY),
	SET_ENTITY_MOTION(0x63, Identifier.vanilla("set_entity_motion"), PacketState.PLAY),
	SET_EQUIPMENT(0x64, Identifier.vanilla("set_equipment"), PacketState.PLAY),
	SET_EXPERIENCE(0x65, Identifier.vanilla("set_experience"), PacketState.PLAY),
	SET_HEALTH(0x66, Identifier.vanilla("set_health"), PacketState.PLAY),
	SET_HELD_SLOT(0x67, Identifier.vanilla("set_held_slot"), PacketState.PLAY),
	SET_OBJECTIVE(0x68, Identifier.vanilla("set_objective"), PacketState.PLAY),
	SET_PASSENGERS(0x69, Identifier.vanilla("set_passengers"), PacketState.PLAY),
	SET_PLAYER_INVENTORY(0x6A, Identifier.vanilla("set_player_inventory"), PacketState.PLAY),
	SET_PLAYER_TEAM(0x6B, Identifier.vanilla("set_player_team"), PacketState.PLAY),
	SET_SCORE(0x6C, Identifier.vanilla("set_score"), PacketState.PLAY),
	SET_SIMULATION_DISTANCE(0x6D, Identifier.vanilla("set_simulation_distance"), PacketState.PLAY),
	SET_SUBTITLE_TEXT(0x6E, Identifier.vanilla("set_subtitle_text"), PacketState.PLAY),
	SET_TIME(0x6F, Identifier.vanilla("set_time"), PacketState.PLAY),
	SET_TITLE_TEXT(0x70, Identifier.vanilla("set_title_text"), PacketState.PLAY),
	SET_TITLES_ANIMATION(0x71, Identifier.vanilla("set_titles_animation"), PacketState.PLAY),
	SOUND_ENTITY(0x72, Identifier.vanilla("sound_entity"), PacketState.PLAY),
	SOUND(0x73, Identifier.vanilla("sound"), PacketState.PLAY),
	START_CONFIGURATION(0x74, Identifier.vanilla("start_configuration"), PacketState.PLAY),
	STOP_SOUND(0x75, Identifier.vanilla("stop_sound"), PacketState.PLAY),
	STORE_COOKIE(0x76, Identifier.vanilla("store_cookie"), PacketState.PLAY),
	SYSTEM_CHAT(0x77, Identifier.vanilla("system_chat"), PacketState.PLAY),
	TAB_LIST(0x78, Identifier.vanilla("tab_list"), PacketState.PLAY),
	TAG_QUERY(0x79, Identifier.vanilla("tag_query"), PacketState.PLAY),
	TAKE_ITEM_ENTITY(0x7A, Identifier.vanilla("take_item_entity"), PacketState.PLAY),
	TELEPORT_ENTITY(0x7B, Identifier.vanilla("teleport_entity"), PacketState.PLAY),
	TEST_INSTANCE_BLOCK_STATUS(0x7C, Identifier.vanilla("test_instance_block_status"), PacketState.PLAY),
	TICKING_STATE(0x7D, Identifier.vanilla("ticking_state"), PacketState.PLAY),
	TICKING_STEP(0x7E, Identifier.vanilla("ticking_step"), PacketState.PLAY),
	TRANSFER(0x7F, Identifier.vanilla("transfer"), PacketState.PLAY),
	UPDATE_ADVANCEMENTS(0x80, Identifier.vanilla("update_advancements"), PacketState.PLAY),
	UPDATE_ATTRIBUTES(0x81, Identifier.vanilla("update_attributes"), PacketState.PLAY),
	UPDATE_MOB_EFFECT(0x82, Identifier.vanilla("update_mob_effect"), PacketState.PLAY),
	UPDATE_RECIPES(0x83, Identifier.vanilla("update_recipes"), PacketState.PLAY),
	UPDATE_TAGS(0x84, Identifier.vanilla("update_tags"), PacketState.PLAY),
	PROJECTILE_POWER(0x85, Identifier.vanilla("projectile_power"), PacketState.PLAY),
	CUSTOM_REPORT_DETAILS(0x86, Identifier.vanilla("custom_report_details"), PacketState.PLAY),
	SERVER_LINKS(0x87, Identifier.vanilla("server_links"), PacketState.PLAY),
	WAYPOINT(0x88, Identifier.vanilla("waypoint"), PacketState.PLAY),
	CLEAR_DIALOG(0x89, Identifier.vanilla("clear_dialog"), PacketState.PLAY),
	SHOW_DIALOG(0x8A, Identifier.vanilla("show_dialog"), PacketState.PLAY);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ClientboundPlayPackets(final int id, final Identifier identifier, final PacketState state) {
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
