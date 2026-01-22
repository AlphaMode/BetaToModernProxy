package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SClientInformationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SConfigurationCustomPayloadPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SCustomQueryAnswerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CRegistryDataPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;

public class ModernPacketRegistry extends PacketRegistry<ModernPackets> {
	public static final ModernPacketRegistry INSTANCE = new ModernPacketRegistry();

	private ModernPacketRegistry() {
		this.registerVanillaPackets();
	}

	@Override
	public RecordPacket<ModernPackets> createPacket(final int packetId, final PacketDirection direction, final PacketState state, final ByteBuf byteBuf) {
		final ModernPackets packetType = ModernPackets.getPacket(packetId, direction, state);
		if (packetType == null) {
			throw new RuntimeException("Packet " + packetId + " is not registered in the packet registry");
		} else {
			return this.getCodec(packetType).decode(byteBuf);
		}
	}

	private void registerVanillaPackets() {
		this.registerHandshakingPackets();
		this.registerPlayPackets();
		this.registerStatusPackets();
		this.registerLoginPackets();
		this.registerConfigurationPackets();
	}

	private void registerHandshakingPackets() {
		this.registerPacket(ServerboundHandshakingPackets.INTENTION, C2SIntentionRecordPacket.CODEC);
	}

	private void registerPlayPackets() {
		// Serverbound
		this.registerPacket(ServerboundPlayPackets.ACCEPT_TELEPORTATION, C2SAcceptTeleportationPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.BLOCK_ENTITY_TAG_QUERY, C2SBlockEntityTagQueryPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.BUNDLE_ITEM_SELECTED, C2SSelectBundleItemPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHANGE_DIFFICULTY, C2SChangeDifficultyPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHANGE_GAME_MODE, C2SChangeGameModePacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHAT_ACK, C2SChatAckPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHAT_COMMAND, C2SChatCommandPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHAT_COMMAND_SIGNED, C2SChatCommandSignedPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHAT, C2SChatPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHAT_SESSION_UPDATE, C2SChatSessionUpdatePacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHUNK_BATCH_RECEIVED, null);
		this.registerPacket(ServerboundPlayPackets.CLIENT_COMMAND, null);
		this.registerPacket(ServerboundPlayPackets.CLIENT_TICK_END, null);
		this.registerPacket(ServerboundPlayPackets.CLIENT_INFORMATION, null);
		this.registerPacket(ServerboundPlayPackets.COMMAND_SUGGESTION, null);
		this.registerPacket(ServerboundPlayPackets.CONFIGURATION_ACKNOWLEDGED, null);
		this.registerPacket(ServerboundPlayPackets.CONTAINER_BUTTON_CLICK, null);
		this.registerPacket(ServerboundPlayPackets.CONTAINER_CLICK, null);
		this.registerPacket(ServerboundPlayPackets.CONTAINER_CLOSE, null);
		this.registerPacket(ServerboundPlayPackets.CONTAINER_SLOT_STATE_CHANGED, null);
		this.registerPacket(ServerboundPlayPackets.COOKIE_RESPONSE, null);
		this.registerPacket(ServerboundPlayPackets.CUSTOM_PAYLOAD, null);
		this.registerPacket(ServerboundPlayPackets.DEBUG_SUBSCRIPTION_REQUEST, null);
		this.registerPacket(ServerboundPlayPackets.EDIT_BOOK, null);
		this.registerPacket(ServerboundPlayPackets.ENTITY_TAG_QUERY, null);
		this.registerPacket(ServerboundPlayPackets.INTERACT, null);
		this.registerPacket(ServerboundPlayPackets.JIGSAW_GENERATE, null);
		this.registerPacket(ServerboundPlayPackets.KEEP_ALIVE, null);
		this.registerPacket(ServerboundPlayPackets.LOCK_DIFFICULTY, null);
		this.registerPacket(ServerboundPlayPackets.MOVE_PLAYER_POS, null);
		this.registerPacket(ServerboundPlayPackets.MOVE_PLAYER_POS_ROT, null);
		this.registerPacket(ServerboundPlayPackets.MOVE_PLAYER_ROT, null);
		this.registerPacket(ServerboundPlayPackets.MOVE_PLAYER_STATUS_ONLY, null);
		this.registerPacket(ServerboundPlayPackets.MOVE_VEHICLE, null);
		this.registerPacket(ServerboundPlayPackets.PADDLE_BOAT, null);
		this.registerPacket(ServerboundPlayPackets.PICK_ITEM_FROM_BLOCK, null);
		this.registerPacket(ServerboundPlayPackets.PICK_ITEM_FROM_ENTITY, null);
		this.registerPacket(ServerboundPlayPackets.PING_REQUEST, null);
		this.registerPacket(ServerboundPlayPackets.PLACE_RECIPE, null);
		this.registerPacket(ServerboundPlayPackets.PLAYER_ABILITIES, null);
		this.registerPacket(ServerboundPlayPackets.PLAYER_ACTION, null);
		this.registerPacket(ServerboundPlayPackets.PLAYER_COMMAND, null);
		this.registerPacket(ServerboundPlayPackets.PLAYER_INPUT, C2SPlayerInputPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.PLAYER_LOADED, C2SPlayerLoadedPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.PONG, C2SPongPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.RECIPE_BOOK_CHANGE_SETTINGS, null);
		this.registerPacket(ServerboundPlayPackets.RECIPE_BOOK_SEEN_RECIPE, null);
		this.registerPacket(ServerboundPlayPackets.RENAME_ITEM, null);
		this.registerPacket(ServerboundPlayPackets.RESOURCE_PACK, null);
		this.registerPacket(ServerboundPlayPackets.SEEN_ADVANCEMENTS, null);
		this.registerPacket(ServerboundPlayPackets.SELECT_TRADE, null);
		this.registerPacket(ServerboundPlayPackets.SET_BEACON, null);
		this.registerPacket(ServerboundPlayPackets.SET_CARRIED_ITEM, null);
		this.registerPacket(ServerboundPlayPackets.SET_COMMAND_BLOCK, null);
		this.registerPacket(ServerboundPlayPackets.SET_COMMAND_MINECART, null);
		this.registerPacket(ServerboundPlayPackets.SET_CREATIVE_MODE_SLOT, null);
		this.registerPacket(ServerboundPlayPackets.SET_JIGSAW_BLOCK, null);
		this.registerPacket(ServerboundPlayPackets.SET_STRUCTURE_BLOCK, null);
		this.registerPacket(ServerboundPlayPackets.SET_TEST_BLOCK, null);
		this.registerPacket(ServerboundPlayPackets.SIGN_UPDATE, null);
		this.registerPacket(ServerboundPlayPackets.SWING, null);
		this.registerPacket(ServerboundPlayPackets.TELEPORT_TO_ENTITY, null);
		this.registerPacket(ServerboundPlayPackets.TEST_INSTANCE_BLOCK_ACTION, null);
		this.registerPacket(ServerboundPlayPackets.USE_ITEM_ON, null);
		this.registerPacket(ServerboundPlayPackets.USE_ITEM, null);
		this.registerPacket(ServerboundPlayPackets.CUSTOM_CLICK_ACTION, null);

		// Clientbound
		this.registerPacket(ClientboundPlayPackets.BUNDLE_DELIMITER, null);
		this.registerPacket(ClientboundPlayPackets.ADD_ENTITY, null);
		this.registerPacket(ClientboundPlayPackets.ANIMATE, null);
		this.registerPacket(ClientboundPlayPackets.AWARD_STATS, null);
		this.registerPacket(ClientboundPlayPackets.BLOCK_CHANGED_ACK, null);
		this.registerPacket(ClientboundPlayPackets.BLOCK_DESTRUCTION, null);
		this.registerPacket(ClientboundPlayPackets.BLOCK_ENTITY_DATA, null);
		this.registerPacket(ClientboundPlayPackets.BLOCK_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.BLOCK_UPDATE, null);
		this.registerPacket(ClientboundPlayPackets.BOSS_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.CHANGE_DIFFICULTY, null);
		this.registerPacket(ClientboundPlayPackets.CHUNK_BATCH_FINISHED, null);
		this.registerPacket(ClientboundPlayPackets.CHUNK_BATCH_START, null);
		this.registerPacket(ClientboundPlayPackets.CHUNKS_BIOMES, null);
		this.registerPacket(ClientboundPlayPackets.CLEAR_TITLES, null);
		this.registerPacket(ClientboundPlayPackets.COMMAND_SUGGESTIONS, null);
		this.registerPacket(ClientboundPlayPackets.COMMANDS, null);
		this.registerPacket(ClientboundPlayPackets.CONTAINER_CLOSE, null);
		this.registerPacket(ClientboundPlayPackets.CONTAINER_SET_CONTENT, null);
		this.registerPacket(ClientboundPlayPackets.CONTAINER_SET_DATA, null);
		this.registerPacket(ClientboundPlayPackets.CONTAINER_SET_SLOT, null);
		this.registerPacket(ClientboundPlayPackets.COOKIE_REQUEST, null);
		this.registerPacket(ClientboundPlayPackets.COOLDOWN, null);
		this.registerPacket(ClientboundPlayPackets.CUSTOM_CHAT_COMPLETIONS, null);
		this.registerPacket(ClientboundPlayPackets.CUSTOM_PAYLOAD, null);
		this.registerPacket(ClientboundPlayPackets.DAMAGE_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.DEBUG_BLOCK_VALUE, null);
		this.registerPacket(ClientboundPlayPackets.DEBUG_CHUNK_VALUE, null);
		this.registerPacket(ClientboundPlayPackets.DEBUG_ENTITY_VALUE, null);
		this.registerPacket(ClientboundPlayPackets.DEBUG_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.DEBUG_SAMPLE, null);
		this.registerPacket(ClientboundPlayPackets.DELETE_CHAT, null);
		this.registerPacket(ClientboundPlayPackets.DISCONNECT, S2CDisconnectPacket.CODEC);
		this.registerPacket(ClientboundPlayPackets.DISGUISED_CHAT, null);
		this.registerPacket(ClientboundPlayPackets.ENTITY_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.ENTITY_POSITION_SYNC, null);
		this.registerPacket(ClientboundPlayPackets.EXPLODE, null);
		this.registerPacket(ClientboundPlayPackets.FORGET_LEVEL_CHUNK, null);
		this.registerPacket(ClientboundPlayPackets.GAME_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.GAME_TEST_HIGHLIGHT_POS, null);
		this.registerPacket(ClientboundPlayPackets.MOUNT_SCREEN_OPEN, null);
		this.registerPacket(ClientboundPlayPackets.HURT_ANIMATION, null);
		this.registerPacket(ClientboundPlayPackets.INITIALIZE_BORDER, null);
		this.registerPacket(ClientboundPlayPackets.KEEP_ALIVE, null);
		this.registerPacket(ClientboundPlayPackets.LEVEL_CHUNK_WITH_LIGHT, null);
		this.registerPacket(ClientboundPlayPackets.LEVEL_EVENT, null);
		this.registerPacket(ClientboundPlayPackets.LEVEL_PARTICLES, null);
		this.registerPacket(ClientboundPlayPackets.LIGHT_UPDATE, null);
		this.registerPacket(ClientboundPlayPackets.LOGIN, null);
		this.registerPacket(ClientboundPlayPackets.MAP_ITEM_DATA, null);
		this.registerPacket(ClientboundPlayPackets.MERCHANT_OFFERS, null);
		this.registerPacket(ClientboundPlayPackets.MOVE_ENTITY_POS, null);
		this.registerPacket(ClientboundPlayPackets.MOVE_ENTITY_POS_ROT, null);
		this.registerPacket(ClientboundPlayPackets.MOVE_MINECART_ALONG_TRACK, null);
		this.registerPacket(ClientboundPlayPackets.MOVE_ENTITY_ROT, null);
		this.registerPacket(ClientboundPlayPackets.MOVE_VEHICLE, null);
		this.registerPacket(ClientboundPlayPackets.OPEN_BOOK, null);
		this.registerPacket(ClientboundPlayPackets.OPEN_SCREEN, null);
		this.registerPacket(ClientboundPlayPackets.OPEN_SIGN_EDITOR, null);
		this.registerPacket(ClientboundPlayPackets.PING, null);
		this.registerPacket(ClientboundPlayPackets.PONG_RESPONSE, null);
		this.registerPacket(ClientboundPlayPackets.PLACE_GHOST_RECIPE, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_ABILITIES, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_CHAT, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_COMBAT_END, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_COMBAT_ENTER, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_COMBAT_KILL, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_INFO_REMOVE, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_INFO_UPDATE, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_LOOK_AT, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_POSITION, null);
		this.registerPacket(ClientboundPlayPackets.PLAYER_ROTATION, null);
		this.registerPacket(ClientboundPlayPackets.RECIPE_BOOK_ADD, null);
		this.registerPacket(ClientboundPlayPackets.RECIPE_BOOK_REMOVE, null);
		this.registerPacket(ClientboundPlayPackets.RECIPE_BOOK_SETTINGS, null);
		this.registerPacket(ClientboundPlayPackets.REMOVE_ENTITIES, null);
		this.registerPacket(ClientboundPlayPackets.REMOVE_MOB_EFFECT, null);
		this.registerPacket(ClientboundPlayPackets.RESET_SCORE, null);
		this.registerPacket(ClientboundPlayPackets.RESOURCE_PACK_POP, null);
		this.registerPacket(ClientboundPlayPackets.RESOURCE_PACK_PUSH, null);
		this.registerPacket(ClientboundPlayPackets.RESPAWN, null);
		this.registerPacket(ClientboundPlayPackets.ROTATE_HEAD, null);
		this.registerPacket(ClientboundPlayPackets.SECTION_BLOCKS_UPDATE, null);
		this.registerPacket(ClientboundPlayPackets.SELECT_ADVANCEMENTS_TAB, null);
		this.registerPacket(ClientboundPlayPackets.SERVER_DATA, null);
		this.registerPacket(ClientboundPlayPackets.SET_ACTION_BAR_TEXT, null);
		this.registerPacket(ClientboundPlayPackets.SET_BORDER_CENTER, null);
		this.registerPacket(ClientboundPlayPackets.SET_BORDER_LERP_SIZE, null);
		this.registerPacket(ClientboundPlayPackets.SET_BORDER_SIZE, null);
		this.registerPacket(ClientboundPlayPackets.SET_BORDER_WARNING_DELAY, null);
		this.registerPacket(ClientboundPlayPackets.SET_BORDER_WARNING_DISTANCE, null);
		this.registerPacket(ClientboundPlayPackets.SET_CAMERA, null);
		this.registerPacket(ClientboundPlayPackets.SET_CHUNK_CACHE_CENTER, null);
		this.registerPacket(ClientboundPlayPackets.SET_CHUNK_CACHE_RADIUS, null);
		this.registerPacket(ClientboundPlayPackets.SET_CURSOR_ITEM, null);
		this.registerPacket(ClientboundPlayPackets.SET_DEFAULT_SPAWN_POSITION, null);
		this.registerPacket(ClientboundPlayPackets.SET_DISPLAY_OBJECTIVE, null);
		this.registerPacket(ClientboundPlayPackets.SET_ENTITY_DATA, null);
		this.registerPacket(ClientboundPlayPackets.SET_ENTITY_LINK, null);
		this.registerPacket(ClientboundPlayPackets.SET_ENTITY_MOTION, null);
		this.registerPacket(ClientboundPlayPackets.SET_EQUIPMENT, null);
		this.registerPacket(ClientboundPlayPackets.SET_EXPERIENCE, null);
		this.registerPacket(ClientboundPlayPackets.SET_HEALTH, null);
		this.registerPacket(ClientboundPlayPackets.SET_HELD_SLOT, null);
		this.registerPacket(ClientboundPlayPackets.SET_OBJECTIVE, null);
		this.registerPacket(ClientboundPlayPackets.SET_PASSENGERS, null);
		this.registerPacket(ClientboundPlayPackets.SET_PLAYER_INVENTORY, null);
		this.registerPacket(ClientboundPlayPackets.SET_PLAYER_TEAM, null);
		this.registerPacket(ClientboundPlayPackets.SET_SCORE, null);
		this.registerPacket(ClientboundPlayPackets.SET_SIMULATION_DISTANCE, null);
		this.registerPacket(ClientboundPlayPackets.SET_SUBTITLE_TEXT, null);
		this.registerPacket(ClientboundPlayPackets.SET_TIME, null);
		this.registerPacket(ClientboundPlayPackets.SET_TITLE_TEXT, null);
		this.registerPacket(ClientboundPlayPackets.SET_TITLES_ANIMATION, null);
		this.registerPacket(ClientboundPlayPackets.SOUND_ENTITY, null);
		this.registerPacket(ClientboundPlayPackets.SOUND, null);
		this.registerPacket(ClientboundPlayPackets.START_CONFIGURATION, null);
		this.registerPacket(ClientboundPlayPackets.STOP_SOUND, null);
		this.registerPacket(ClientboundPlayPackets.STORE_COOKIE, null);
		this.registerPacket(ClientboundPlayPackets.SYSTEM_CHAT, null);
		this.registerPacket(ClientboundPlayPackets.TAB_LIST, null);
		this.registerPacket(ClientboundPlayPackets.TAG_QUERY, null);
		this.registerPacket(ClientboundPlayPackets.TAKE_ITEM_ENTITY, null);
		this.registerPacket(ClientboundPlayPackets.TELEPORT_ENTITY, null);
		this.registerPacket(ClientboundPlayPackets.TEST_INSTANCE_BLOCK_STATUS, null);
		this.registerPacket(ClientboundPlayPackets.TICKING_STATE, null);
		this.registerPacket(ClientboundPlayPackets.TICKING_STEP, null);
		this.registerPacket(ClientboundPlayPackets.TRANSFER, null);
		this.registerPacket(ClientboundPlayPackets.UPDATE_ADVANCEMENTS, null);
		this.registerPacket(ClientboundPlayPackets.UPDATE_ATTRIBUTES, null);
		this.registerPacket(ClientboundPlayPackets.UPDATE_MOB_EFFECT, null);
		this.registerPacket(ClientboundPlayPackets.UPDATE_RECIPES, null);
		this.registerPacket(ClientboundPlayPackets.UPDATE_TAGS, null);
		this.registerPacket(ClientboundPlayPackets.PROJECTILE_POWER, null);
		this.registerPacket(ClientboundPlayPackets.CUSTOM_REPORT_DETAILS, null);
		this.registerPacket(ClientboundPlayPackets.SERVER_LINKS, null);
		this.registerPacket(ClientboundPlayPackets.WAYPOINT, null);
		this.registerPacket(ClientboundPlayPackets.CLEAR_DIALOG, null);
		this.registerPacket(ClientboundPlayPackets.SHOW_DIALOG, null);
	}

	private void registerStatusPackets() {
		// Serverbound
		this.registerPacket(ServerboundStatusPackets.STATUS_REQUEST, C2SStatusRequestPacket.CODEC);
		this.registerPacket(ServerboundStatusPackets.PING_REQUEST, null);

		// Clientbound
		this.registerPacket(ClientboundStatusPackets.STATUS_RESPONSE, S2CStatusResponsePacket.CODEC);
		this.registerPacket(ClientboundStatusPackets.PONG_RESPONSE, null);
	}

	private void registerLoginPackets() {
		// Serverbound
		this.registerPacket(ServerboundLoginPackets.HELLO, C2SHelloPacket.CODEC);
		this.registerPacket(ServerboundLoginPackets.KEY, null);
		this.registerPacket(ServerboundLoginPackets.CUSTOM_QUERY_ANSWER, C2SCustomQueryAnswerPacket.CODEC);
		this.registerPacket(ServerboundLoginPackets.ACKNOWLEDGED, C2SLoginAcknowledgedPacket.CODEC);
		this.registerPacket(ServerboundLoginPackets.COOKIE_RESPONSE, null);

		// Clientbound
		this.registerPacket(ClientboundLoginPackets.LOGIN_DISCONNECT, null);
		this.registerPacket(ClientboundLoginPackets.HELLO, S2CHelloPacket.CODEC);
		this.registerPacket(ClientboundLoginPackets.FINISHED, S2CLoginFinishedPacket.CODEC);
		this.registerPacket(ClientboundLoginPackets.COMPRESSION, null);
		this.registerPacket(ClientboundLoginPackets.CUSTOM_QUERY, null);
		this.registerPacket(ClientboundLoginPackets.COOKIE_REQUEST, null);
	}

	private void registerConfigurationPackets() {
		// Serverbound
		this.registerPacket(ServerboundConfigurationPackets.CLIENT_INFORMATION, C2SClientInformationPacket.CODEC);
		this.registerPacket(ServerboundConfigurationPackets.COOKIE_RESPONSE, null);
		this.registerPacket(ServerboundConfigurationPackets.CUSTOM_PAYLOAD, C2SConfigurationCustomPayloadPacket.CODEC);
		this.registerPacket(ServerboundConfigurationPackets.FINISH_CONFIGURATION, C2SFinishConfigurationPacket.CODEC);
		this.registerPacket(ServerboundConfigurationPackets.KEEP_ALIVE, C2SKeepAlivePacket.CODEC);
		this.registerPacket(ServerboundConfigurationPackets.PONG, null);
		this.registerPacket(ServerboundConfigurationPackets.RESOURCE_PACK, null);
		this.registerPacket(ServerboundConfigurationPackets.SELECT_KNOWN_PACKS, null);
		this.registerPacket(ServerboundConfigurationPackets.CUSTOM_CLICK_ACTION, null);
		this.registerPacket(ServerboundConfigurationPackets.ACCEPT_CODE_OF_CONDUCT, null);

		// Clientbound
		this.registerPacket(ClientboundConfigurationPackets.COOKIE_REQUEST, null);
		this.registerPacket(ClientboundConfigurationPackets.CUSTOM_PAYLOAD, null);
		this.registerPacket(ClientboundConfigurationPackets.DISCONNECT, S2CConfigurationDisconnectPacket.CODEC);
		this.registerPacket(ClientboundConfigurationPackets.FINISH_CONFIGURATION, S2CFinishConfigurationPacket.CODEC);
		this.registerPacket(ClientboundConfigurationPackets.KEEP_ALIVE, null);
		this.registerPacket(ClientboundConfigurationPackets.PING, null);
		this.registerPacket(ClientboundConfigurationPackets.RESET_CHAT, null);
		this.registerPacket(ClientboundConfigurationPackets.REGISTRY_DATA, S2CRegistryDataPacket.CODEC);
		this.registerPacket(ClientboundConfigurationPackets.RESOURCE_PACK_POP, null);
		this.registerPacket(ClientboundConfigurationPackets.RESOURCE_PACK_PUSH, null);
		this.registerPacket(ClientboundConfigurationPackets.STORE_COOKIE, null);
		this.registerPacket(ClientboundConfigurationPackets.TRANSFER, null);
		this.registerPacket(ClientboundConfigurationPackets.UPDATE_ENABLED_FEATURES, null);
		this.registerPacket(ClientboundConfigurationPackets.UPDATE_TAGS, null);
		this.registerPacket(ClientboundConfigurationPackets.SELECT_KNOWN_PACKS, null);
		this.registerPacket(ClientboundConfigurationPackets.CUSTOM_REPORT_DETAILS, null);
		this.registerPacket(ClientboundConfigurationPackets.SERVER_LINKS, null);
		this.registerPacket(ClientboundConfigurationPackets.CLEAR_DIALOG, null);
		this.registerPacket(ClientboundConfigurationPackets.SHOW_DIALOG, null);
		this.registerPacket(ClientboundConfigurationPackets.CODE_OF_CONDUCT, null);
	}
}
