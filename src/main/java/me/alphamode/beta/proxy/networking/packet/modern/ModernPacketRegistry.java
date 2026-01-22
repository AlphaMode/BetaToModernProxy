package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundLoginPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundStatusPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
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
		this.registerPacket(ServerboundPlayPackets.ACCEPT_TELEPORTATION, C2SAcceptTeleportationRecordPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.BLOCK_ENTITY_TAG_QUERY, C2SBlockEntityTagQueryPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.BUNDLE_ITEM_SELECTED, C2SSelectBundleItemPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHANGE_DIFFICULTY, C2SChangeDifficultyPacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHANGE_GAME_MODE, C2SChangeGameModePacket.CODEC);
		this.registerPacket(ServerboundPlayPackets.CHAT_ACK, null);
		this.registerPacket(ServerboundPlayPackets.CHAT_COMMAND, null);
		this.registerPacket(ServerboundPlayPackets.CHAT_COMMAND_SIGNED, null);
		this.registerPacket(ServerboundPlayPackets.CHAT, null);
		this.registerPacket(ServerboundPlayPackets.CHAT_SESSION_UPDATE, null);
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
		this.registerPacket(ServerboundPlayPackets.PLAYER_INPUT, null);
		this.registerPacket(ServerboundPlayPackets.PLAYER_LOADED, null);
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

	}

	private void registerStatusPackets() {
		// Serverbound
		this.registerPacket(ServerboundStatusPackets.STATUS_REQUEST, C2SStatusRequestPacket.CODEC);

		// Clientbound
		this.registerPacket(ClientboundStatusPackets.STATUS_RESPONSE, S2CStatusResponsePacket.CODEC);
	}

	private void registerLoginPackets() {
		// Serverbound
		this.registerPacket(ServerboundLoginPackets.HELLO, C2SHelloPacket.CODEC);
		this.registerPacket(ServerboundLoginPackets.KEY, null);
		this.registerPacket(ServerboundLoginPackets.CUSTOM_QUERY_ANSWER, null);
		this.registerPacket(ServerboundLoginPackets.ACKNOWLEDGED, null);
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
		this.registerPacket(ServerboundConfigurationPackets.CLIENT_INFORMATION, null);
		this.registerPacket(ServerboundConfigurationPackets.COOKIE_RESPONSE, null);
		this.registerPacket(ServerboundConfigurationPackets.CUSTOM_PAYLOAD, null);
		this.registerPacket(ServerboundConfigurationPackets.FINISH_CONFIGURATION, null);
		this.registerPacket(ServerboundConfigurationPackets.KEEP_ALIVE, null);
		this.registerPacket(ServerboundConfigurationPackets.PONG, null);
		this.registerPacket(ServerboundConfigurationPackets.RESOURCE_PACK, null);
		this.registerPacket(ServerboundConfigurationPackets.SELECT_KNOWN_PACKS, null);
		this.registerPacket(ServerboundConfigurationPackets.CUSTOM_CLICK_ACTION, null);
		this.registerPacket(ServerboundConfigurationPackets.ACCEPT_CODE_OF_CONDUCT, null);

		// Clientbound
		this.registerPacket(ClientboundConfigurationPackets.COOKIE_REQUEST, null);
		this.registerPacket(ClientboundConfigurationPackets.CUSTOM_PAYLOAD, null);
		this.registerPacket(ClientboundConfigurationPackets.DISCONNECT, null);
		this.registerPacket(ClientboundConfigurationPackets.FINISH_CONFIGURATION, null);
		this.registerPacket(ClientboundConfigurationPackets.KEEP_ALIVE, null);
		this.registerPacket(ClientboundConfigurationPackets.PING, null);
		this.registerPacket(ClientboundConfigurationPackets.RESET_CHAT, null);
		this.registerPacket(ClientboundConfigurationPackets.REGISTRY_DATA, null);
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
