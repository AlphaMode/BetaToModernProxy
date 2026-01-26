package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import net.lenni0451.mcstructs.core.Identifier;

public enum ClientboundPlayPackets implements ModernClientboundPackets {
	BUNDLE_DELIMITER(0x00, Identifier.defaultNamespace("bundle_delimiter"), PacketState.PLAY),
	ADD_ENTITY(0x01, Identifier.defaultNamespace("add_entity"), PacketState.PLAY),
	ANIMATE(0x02, Identifier.defaultNamespace("animate"), PacketState.PLAY),
	AWARD_STATS(0x03, Identifier.defaultNamespace("award_stats"), PacketState.PLAY),
	BLOCK_CHANGED_ACK(0x04, Identifier.defaultNamespace("block_changed_ack"), PacketState.PLAY),
	BLOCK_DESTRUCTION(0x05, Identifier.defaultNamespace("block_destruction"), PacketState.PLAY),
	BLOCK_ENTITY_DATA(0x06, Identifier.defaultNamespace("block_entity_data"), PacketState.PLAY),
	BLOCK_EVENT(0x07, Identifier.defaultNamespace("block_event"), PacketState.PLAY),
	BLOCK_UPDATE(0x08, Identifier.defaultNamespace("block_update"), PacketState.PLAY),
	BOSS_EVENT(0x09, Identifier.defaultNamespace("boss_event"), PacketState.PLAY),
	CHANGE_DIFFICULTY(0x0A, Identifier.defaultNamespace("change_difficulty"), PacketState.PLAY),
	CHUNK_BATCH_FINISHED(0x0B, Identifier.defaultNamespace("chunk_batch_finished"), PacketState.PLAY),
	CHUNK_BATCH_START(0x0C, Identifier.defaultNamespace("chunk_batch_start"), PacketState.PLAY),
	CHUNKS_BIOMES(0x0D, Identifier.defaultNamespace("chunks_biomes"), PacketState.PLAY),
	CLEAR_TITLES(0x0E, Identifier.defaultNamespace("clear_titles"), PacketState.PLAY),
	COMMAND_SUGGESTIONS(0x0F, Identifier.defaultNamespace("command_suggestions"), PacketState.PLAY),
	COMMANDS(0x10, Identifier.defaultNamespace("commands"), PacketState.PLAY),
	CONTAINER_CLOSE(0x11, Identifier.defaultNamespace("container_close"), PacketState.PLAY),
	CONTAINER_SET_CONTENT(0x12, Identifier.defaultNamespace("container_set_content"), PacketState.PLAY),
	CONTAINER_SET_DATA(0x13, Identifier.defaultNamespace("container_set_data"), PacketState.PLAY),
	CONTAINER_SET_SLOT(0x14, Identifier.defaultNamespace("container_set_slot"), PacketState.PLAY),
	COOKIE_REQUEST(0x15, Identifier.defaultNamespace("cookie_request"), PacketState.PLAY),
	COOLDOWN(0x16, Identifier.defaultNamespace("cooldown"), PacketState.PLAY),
	CUSTOM_CHAT_COMPLETIONS(0x17, Identifier.defaultNamespace("custom_chat_completions"), PacketState.PLAY),
	CUSTOM_PAYLOAD(0x18, Identifier.defaultNamespace("custom_payload"), PacketState.PLAY),
	DAMAGE_EVENT(0x19, Identifier.defaultNamespace("damage_event"), PacketState.PLAY),
	DEBUG_BLOCK_VALUE(0x1A, Identifier.defaultNamespace("debug/block_value"), PacketState.PLAY),
	DEBUG_CHUNK_VALUE(0x1B, Identifier.defaultNamespace("debug/chunk_value"), PacketState.PLAY),
	DEBUG_ENTITY_VALUE(0x1C, Identifier.defaultNamespace("debug/entity_value"), PacketState.PLAY),
	DEBUG_EVENT(0x1D, Identifier.defaultNamespace("debug/event"), PacketState.PLAY),
	DEBUG_SAMPLE(0x1E, Identifier.defaultNamespace("debug_sample"), PacketState.PLAY),
	DELETE_CHAT(0x1F, Identifier.defaultNamespace("delete_chat"), PacketState.PLAY),
	DISCONNECT(0x20, Identifier.defaultNamespace("disconnect"), PacketState.PLAY),
	DISGUISED_CHAT(0x21, Identifier.defaultNamespace("disguised_chat"), PacketState.PLAY),
	ENTITY_EVENT(0x22, Identifier.defaultNamespace("entity_event"), PacketState.PLAY),
	ENTITY_POSITION_SYNC(0x23, Identifier.defaultNamespace("entity_position_sync"), PacketState.PLAY),
	EXPLODE(0x24, Identifier.defaultNamespace("explode"), PacketState.PLAY),
	FORGET_LEVEL_CHUNK(0x25, Identifier.defaultNamespace("forget_level_chunk"), PacketState.PLAY),
	GAME_EVENT(0x26, Identifier.defaultNamespace("game_event"), PacketState.PLAY),
	GAME_TEST_HIGHLIGHT_POS(0x27, Identifier.defaultNamespace("game_test_highlight_pos"), PacketState.PLAY),
	MOUNT_SCREEN_OPEN(0x28, Identifier.defaultNamespace("mount_screen_open"), PacketState.PLAY),
	HURT_ANIMATION(0x29, Identifier.defaultNamespace("hurt_animation"), PacketState.PLAY),
	INITIALIZE_BORDER(0x2A, Identifier.defaultNamespace("initialize_border"), PacketState.PLAY),
	KEEP_ALIVE(0x2B, Identifier.defaultNamespace("keep_alive"), PacketState.PLAY),
	LEVEL_CHUNK_WITH_LIGHT(0x2C, Identifier.defaultNamespace("level_chunk_with_light"), PacketState.PLAY),
	LEVEL_EVENT(0x2D, Identifier.defaultNamespace("level_event"), PacketState.PLAY),
	LEVEL_PARTICLES(0x2E, Identifier.defaultNamespace("level_particles"), PacketState.PLAY),
	LIGHT_UPDATE(0x2F, Identifier.defaultNamespace("light_update"), PacketState.PLAY),
	LOGIN(0x30, Identifier.defaultNamespace("login"), PacketState.PLAY),
	MAP_ITEM_DATA(0x31, Identifier.defaultNamespace("map_item_data"), PacketState.PLAY),
	MERCHANT_OFFERS(0x32, Identifier.defaultNamespace("merchant_offers"), PacketState.PLAY),
	MOVE_ENTITY_POS(0x33, Identifier.defaultNamespace("move_entity_pos"), PacketState.PLAY),
	MOVE_ENTITY_POS_ROT(0x34, Identifier.defaultNamespace("move_entity_pos_rot"), PacketState.PLAY),
	MOVE_MINECART_ALONG_TRACK(0x35, Identifier.defaultNamespace("move_minecart_along_track"), PacketState.PLAY),
	MOVE_ENTITY_ROT(0x36, Identifier.defaultNamespace("move_entity_rot"), PacketState.PLAY),
	MOVE_VEHICLE(0x37, Identifier.defaultNamespace("move_vehicle"), PacketState.PLAY),
	OPEN_BOOK(0x38, Identifier.defaultNamespace("open_book"), PacketState.PLAY),
	OPEN_SCREEN(0x39, Identifier.defaultNamespace("open_screen"), PacketState.PLAY),
	OPEN_SIGN_EDITOR(0x3A, Identifier.defaultNamespace("open_sign_editor"), PacketState.PLAY),
	PING(0x3B, Identifier.defaultNamespace("ping"), PacketState.PLAY),
	PONG_RESPONSE(0x3C, Identifier.defaultNamespace("pong_response"), PacketState.PLAY),
	PLACE_GHOST_RECIPE(0x3D, Identifier.defaultNamespace("place_ghost_recipe"), PacketState.PLAY),
	PLAYER_ABILITIES(0x3E, Identifier.defaultNamespace("player_abilities"), PacketState.PLAY),
	PLAYER_CHAT(0x3F, Identifier.defaultNamespace("player_chat"), PacketState.PLAY),
	PLAYER_COMBAT_END(0x40, Identifier.defaultNamespace("player_combat_end"), PacketState.PLAY),
	PLAYER_COMBAT_ENTER(0x41, Identifier.defaultNamespace("player_combat_enter"), PacketState.PLAY),
	PLAYER_COMBAT_KILL(0x42, Identifier.defaultNamespace("player_combat_kill"), PacketState.PLAY),
	PLAYER_INFO_REMOVE(0x43, Identifier.defaultNamespace("player_info_remove"), PacketState.PLAY),
	PLAYER_INFO_UPDATE(0x44, Identifier.defaultNamespace("player_info_update"), PacketState.PLAY),
	PLAYER_LOOK_AT(0x45, Identifier.defaultNamespace("player_look_at"), PacketState.PLAY),
	PLAYER_POSITION(0x46, Identifier.defaultNamespace("player_position"), PacketState.PLAY),
	PLAYER_ROTATION(0x47, Identifier.defaultNamespace("player_rotation"), PacketState.PLAY),
	RECIPE_BOOK_ADD(0x48, Identifier.defaultNamespace("recipe_book_add"), PacketState.PLAY),
	RECIPE_BOOK_REMOVE(0x49, Identifier.defaultNamespace("recipe_book_remove"), PacketState.PLAY),
	RECIPE_BOOK_SETTINGS(0x4A, Identifier.defaultNamespace("recipe_book_settings"), PacketState.PLAY),
	REMOVE_ENTITIES(0x4B, Identifier.defaultNamespace("remove_entities"), PacketState.PLAY),
	REMOVE_MOB_EFFECT(0x4C, Identifier.defaultNamespace("remove_mob_effect"), PacketState.PLAY),
	RESET_SCORE(0x4D, Identifier.defaultNamespace("reset_score"), PacketState.PLAY),
	RESOURCE_PACK_POP(0x4E, Identifier.defaultNamespace("resource_pack_pop"), PacketState.PLAY),
	RESOURCE_PACK_PUSH(0x4F, Identifier.defaultNamespace("resource_pack_push"), PacketState.PLAY),
	RESPAWN(0x50, Identifier.defaultNamespace("respawn"), PacketState.PLAY),
	ROTATE_HEAD(0x51, Identifier.defaultNamespace("rotate_head"), PacketState.PLAY),
	SECTION_BLOCKS_UPDATE(0x52, Identifier.defaultNamespace("section_blocks_update"), PacketState.PLAY),
	SELECT_ADVANCEMENTS_TAB(0x53, Identifier.defaultNamespace("select_advancements_tab"), PacketState.PLAY),
	SERVER_DATA(0x54, Identifier.defaultNamespace("server_data"), PacketState.PLAY),
	SET_ACTION_BAR_TEXT(0x55, Identifier.defaultNamespace("set_action_bar_text"), PacketState.PLAY),
	SET_BORDER_CENTER(0x56, Identifier.defaultNamespace("set_border_center"), PacketState.PLAY),
	SET_BORDER_LERP_SIZE(0x57, Identifier.defaultNamespace("set_border_lerp_size"), PacketState.PLAY),
	SET_BORDER_SIZE(0x58, Identifier.defaultNamespace("set_border_size"), PacketState.PLAY),
	SET_BORDER_WARNING_DELAY(0x59, Identifier.defaultNamespace("set_border_warning_delay"), PacketState.PLAY),
	SET_BORDER_WARNING_DISTANCE(0x5A, Identifier.defaultNamespace("set_border_warning_distance"), PacketState.PLAY),
	SET_CAMERA(0x5B, Identifier.defaultNamespace("set_camera"), PacketState.PLAY),
	SET_CHUNK_CACHE_CENTER(0x5C, Identifier.defaultNamespace("set_chunk_cache_center"), PacketState.PLAY),
	SET_CHUNK_CACHE_RADIUS(0x5D, Identifier.defaultNamespace("set_chunk_cache_radius"), PacketState.PLAY),
	SET_CURSOR_ITEM(0x5E, Identifier.defaultNamespace("set_cursor_item"), PacketState.PLAY),
	SET_DEFAULT_SPAWN_POSITION(0x5F, Identifier.defaultNamespace("set_default_spawn_position"), PacketState.PLAY),
	SET_DISPLAY_OBJECTIVE(0x60, Identifier.defaultNamespace("set_display_objective"), PacketState.PLAY),
	SET_ENTITY_DATA(0x61, Identifier.defaultNamespace("set_entity_data"), PacketState.PLAY),
	SET_ENTITY_LINK(0x62, Identifier.defaultNamespace("set_entity_link"), PacketState.PLAY),
	SET_ENTITY_MOTION(0x63, Identifier.defaultNamespace("set_entity_motion"), PacketState.PLAY),
	SET_EQUIPMENT(0x64, Identifier.defaultNamespace("set_equipment"), PacketState.PLAY),
	SET_EXPERIENCE(0x65, Identifier.defaultNamespace("set_experience"), PacketState.PLAY),
	SET_HEALTH(0x66, Identifier.defaultNamespace("set_health"), PacketState.PLAY),
	SET_HELD_SLOT(0x67, Identifier.defaultNamespace("set_held_slot"), PacketState.PLAY),
	SET_OBJECTIVE(0x68, Identifier.defaultNamespace("set_objective"), PacketState.PLAY),
	SET_PASSENGERS(0x69, Identifier.defaultNamespace("set_passengers"), PacketState.PLAY),
	SET_PLAYER_INVENTORY(0x6A, Identifier.defaultNamespace("set_player_inventory"), PacketState.PLAY),
	SET_PLAYER_TEAM(0x6B, Identifier.defaultNamespace("set_player_team"), PacketState.PLAY),
	SET_SCORE(0x6C, Identifier.defaultNamespace("set_score"), PacketState.PLAY),
	SET_SIMULATION_DISTANCE(0x6D, Identifier.defaultNamespace("set_simulation_distance"), PacketState.PLAY),
	SET_SUBTITLE_TEXT(0x6E, Identifier.defaultNamespace("set_subtitle_text"), PacketState.PLAY),
	SET_TIME(0x6F, Identifier.defaultNamespace("set_time"), PacketState.PLAY),
	SET_TITLE_TEXT(0x70, Identifier.defaultNamespace("set_title_text"), PacketState.PLAY),
	SET_TITLES_ANIMATION(0x71, Identifier.defaultNamespace("set_titles_animation"), PacketState.PLAY),
	SOUND_ENTITY(0x72, Identifier.defaultNamespace("sound_entity"), PacketState.PLAY),
	SOUND(0x73, Identifier.defaultNamespace("sound"), PacketState.PLAY),
	START_CONFIGURATION(0x74, Identifier.defaultNamespace("start_configuration"), PacketState.PLAY),
	STOP_SOUND(0x75, Identifier.defaultNamespace("stop_sound"), PacketState.PLAY),
	STORE_COOKIE(0x76, Identifier.defaultNamespace("store_cookie"), PacketState.PLAY),
	SYSTEM_CHAT(0x77, Identifier.defaultNamespace("system_chat"), PacketState.PLAY),
	TAB_LIST(0x78, Identifier.defaultNamespace("tab_list"), PacketState.PLAY),
	TAG_QUERY(0x79, Identifier.defaultNamespace("tag_query"), PacketState.PLAY),
	TAKE_ITEM_ENTITY(0x7A, Identifier.defaultNamespace("take_item_entity"), PacketState.PLAY),
	TELEPORT_ENTITY(0x7B, Identifier.defaultNamespace("teleport_entity"), PacketState.PLAY),
	TEST_INSTANCE_BLOCK_STATUS(0x7C, Identifier.defaultNamespace("test_instance_block_status"), PacketState.PLAY),
	TICKING_STATE(0x7D, Identifier.defaultNamespace("ticking_state"), PacketState.PLAY),
	TICKING_STEP(0x7E, Identifier.defaultNamespace("ticking_step"), PacketState.PLAY),
	TRANSFER(0x7F, Identifier.defaultNamespace("transfer"), PacketState.PLAY),
	UPDATE_ADVANCEMENTS(0x80, Identifier.defaultNamespace("update_advancements"), PacketState.PLAY),
	UPDATE_ATTRIBUTES(0x81, Identifier.defaultNamespace("update_attributes"), PacketState.PLAY),
	UPDATE_MOB_EFFECT(0x82, Identifier.defaultNamespace("update_mob_effect"), PacketState.PLAY),
	UPDATE_RECIPES(0x83, Identifier.defaultNamespace("update_recipes"), PacketState.PLAY),
	UPDATE_TAGS(0x84, Identifier.defaultNamespace("update_tags"), PacketState.PLAY),
	PROJECTILE_POWER(0x85, Identifier.defaultNamespace("projectile_power"), PacketState.PLAY),
	CUSTOM_REPORT_DETAILS(0x86, Identifier.defaultNamespace("custom_report_details"), PacketState.PLAY),
	SERVER_LINKS(0x87, Identifier.defaultNamespace("server_links"), PacketState.PLAY),
	WAYPOINT(0x88, Identifier.defaultNamespace("waypoint"), PacketState.PLAY),
	CLEAR_DIALOG(0x89, Identifier.defaultNamespace("clear_dialog"), PacketState.PLAY),
	SHOW_DIALOG(0x8A, Identifier.defaultNamespace("show_dialog"), PacketState.PLAY);

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

	public static ClientboundPlayPackets getPacket(final int id) {
		for (final ClientboundPlayPackets packet : ClientboundPlayPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
