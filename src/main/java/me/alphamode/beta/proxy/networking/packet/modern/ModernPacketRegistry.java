package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.*;

public class ModernPacketRegistry extends PacketRegistry<ModernPackets> {
	public static final ModernPacketRegistry INSTANCE = new ModernPacketRegistry();
	private PacketState state = PacketState.HANDSHAKING;

	private ModernPacketRegistry() {
		this.registerVanillaPackets();
	}

	@Override
	public RecordPacket<ModernPackets> createPacket(final int packetId, final PacketDirection direction, final ByteBuf byteBuf) {
		final ModernPackets packetType = ModernPackets.getPacket(packetId, direction, this.state);
		if (packetType == null) {
			throw new RuntimeException("Packet " + packetId + " is not registered in the packet registry");
		} else {
			return this.getCodec(packetType).decode(byteBuf);
		}
	}

	public PacketState getState() {
		return state;
	}

	public void setState(final PacketState state) {
		this.state = state;
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
		this.registerPacket(ServerboundPlayPackets.PONG, null);
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

	}

	private void registerLoginPackets() {
	}

	private void registerConfigurationPackets() {

	}
}
