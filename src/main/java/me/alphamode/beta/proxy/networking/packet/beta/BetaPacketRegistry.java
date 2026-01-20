package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.*;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

import java.util.EnumMap;
import java.util.Map;

public class BetaPacketRegistry {
	public static final BetaPacketRegistry INSTANCE = new BetaPacketRegistry();
	private final Map<BetaPackets, StreamCodec<ByteBuf, ? extends RecordPacket>> registry = new EnumMap<>(BetaPackets.class);

	public BetaPacketRegistry() {
		this.registerVanillaPackets();
	}

	public RecordPacket createPacket(final int packetId, final ByteBuf byteBuf) {
		final BetaPackets packetType = BetaPackets.getPacket(packetId);
		if (packetType == null) {
			throw new IllegalArgumentException("Packet " + packetId + " is not registered in the packet registry");
		} else {
			IO.println(packetType);
			return getCodec(packetType).decode(byteBuf);
		}
	}

	public <T extends RecordPacket> StreamCodec<ByteBuf, T> getCodec(final BetaPackets type) {
		if (!registry.containsKey(type)) {
			throw new IllegalArgumentException("Packet type" + type + " is not registered in the packet registry");
		}
		return (StreamCodec<ByteBuf, T>) this.registry.get(type);
	}

	protected final void registerPacket(final BetaPackets packetType, final StreamCodec<ByteBuf, ? extends RecordPacket> packetCreator) {
		this.registry.put(packetType, packetCreator);
	}

	public void registerVanillaPackets() {
		this.registerPacket(BetaPackets.KEEP_ALIVE, KeepAlivePacket.CODEC);
		this.registerPacket(BetaPackets.LOGIN, LoginPacket.CODEC);
		this.registerPacket(BetaPackets.HANDSHAKE, HandshakePacket.CODEC);
		this.registerPacket(BetaPackets.CHAT, ChatPacket.CODEC);
		this.registerPacket(BetaPackets.SET_TIME, SetTimePacket.CODEC);
		this.registerPacket(BetaPackets.SET_EQUIPPED_ITEM, SetEquippedItemPacket.CODEC);
		this.registerPacket(BetaPackets.SET_SPAWN_POSITION, SetSpawnPositionPacket.CODEC);
		this.registerPacket(BetaPackets.INTERACT, InteractPacket.CODEC);
		this.registerPacket(BetaPackets.SET_HEALTH, SetHealthPacket.CODEC);
		this.registerPacket(BetaPackets.PLAYER_CHANGE_DIMENSION, PlayerChangeDimensionPacket.CODEC);
//		this.registerPacket(BetaPackets.MOVE_PLAYER, MovePlayerPacket.CODEC);
//		this.registerPacket(BetaPackets.MOVE_PLAYER_POS, MovePlayerPacket.Pos.CODEC);
//		this.registerPacket(BetaPackets.MOVE_PLAYER_ROT, MovePlayerPacket.Rot.CODEC);
		this.registerPacket(BetaPackets.MOVE_PLAYER_POS_ROT, MovePlayerPacket.PosRot.CODEC);
//		this.registerPacket(BetaPackets.PLAYER_ACTION, PlayerActionPacket.CODEC);
//		this.registerPacket(BetaPackets.USE_ITEM, UseItemPacket.CODEC);
//		this.registerPacket(BetaPackets.SET_CARRIED_ITEM, SetCarriedItemPacket.CODEC);
//		this.registerPacket(BetaPackets.INTERACTION, InteractionPacket.CODEC);
//		this.registerPacket(BetaPackets.ANIMATE, AnimatePacket.CODEC);
//		this.registerPacket(BetaPackets.PLAYER_COMMAND, PlayerCommandPacket.CODEC);
//		this.registerPacket(BetaPackets.ADD_PLAYER, AddPlayerPacket.CODEC);
//		this.registerPacket(BetaPackets.ADD_ITEM_ENTITY, AddItemEntityPacket.CODEC);
//		this.registerPacket(BetaPackets.TAKE_ITEM_ENTITY, TakeItemEntityPacket.CODEC);
//		this.registerPacket(BetaPackets.ADD_ENTITY, AddEntityPacket.CODEC);
		this.registerPacket(BetaPackets.ADD_MOB, AddMobPacket.CODEC);
		this.registerPacket(BetaPackets.ADD_PAINTING, AddPaintingPacket.CODEC);
		this.registerPacket(BetaPackets.PLAYER_INPUT, PlayerInputPacket.CODEC);
		this.registerPacket(BetaPackets.SET_ENTITY_MOTION, SetEntityMotionPacket.CODEC);
//		this.registerPacket(BetaPackets.REMOVE_ENTITY, RemoveEntityPacket.CODEC);
		this.registerPacket(BetaPackets.MOVE_ENTITY, MoveEntityPacket.CODEC);
		this.registerPacket(BetaPackets.MOVE_ENTITY_POS, MoveEntityPacket.Pos.CODEC);
		this.registerPacket(BetaPackets.MOVE_ENTITY_ROT, MoveEntityPacket.Rot.CODEC);
		this.registerPacket(BetaPackets.MOVE_ENTITY_POS_ROT, MoveEntityPacket.PosRot.CODEC);
//		this.registerPacket(BetaPackets.TELEPORT_ENTITY, TeleportEntityPacket.CODEC);
//		this.registerPacket(BetaPackets.ENTITY_EVENT, EntityEventPacket.CODEC);
//		this.registerPacket(BetaPackets.SET_RIDING, SetRidingPacket.CODEC);
//		this.registerPacket(BetaPackets.SET_ENTITY_DATA, SetEntityDataPacket.CODEC);
		this.registerPacket(BetaPackets.CHUNK_VISIBILITY, ChunkVisibilityPacket.CODEC);
		this.registerPacket(BetaPackets.BLOCK_REGION_UPDATE, BlockRegionUpdatePacket.CODEC);
		this.registerPacket(BetaPackets.CHUNK_TILES_UPDATE, ChunkTilesUpdatePacket.CODEC);
		this.registerPacket(BetaPackets.TILE_UPDATE, TileUpdatePacket.CODEC);
//		this.registerPacket(BetaPackets.TILE_EVENT, TileEventPacket.CODEC);
//		this.registerPacket(BetaPackets.EXPLODE, ExplodePacket.CODEC);
//		this.registerPacket(BetaPackets.LEVEL_EVENT, LevelEventPacket.CODEC);
		this.registerPacket(BetaPackets.GAME_EVENT, GameEventPacket.CODEC);
		this.registerPacket(BetaPackets.ADD_GLOBAL_ENTITY, AddGlobalEntityPacket.CODEC);
//		this.registerPacket(BetaPackets.CONTAINER_OPEN, ContainerOpenPacket.CODEC);
//		this.registerPacket(BetaPackets.CONTAINER_CLOSE, ContainerClosePacket.CODEC);
//		this.registerPacket(BetaPackets.CONTAINER_CLICK, ContainerClickPacket.CODEC);
		this.registerPacket(BetaPackets.CONTAINER_SET_SLOT, ContainerSetSlotPacket.CODEC);
		this.registerPacket(BetaPackets.CONTAINER_SET_CONTENT, ContainerSetContentPacket.CODEC);
		this.registerPacket(BetaPackets.CONTAINER_SET_DATA, ContainerSetDataPacket.CODEC);
		this.registerPacket(BetaPackets.CONTAINER_ACK, ContainerAckPacket.CODEC);
		this.registerPacket(BetaPackets.SIGN_UPDATE, SignUpdatePacket.CODEC);
//		this.registerPacket(BetaPackets.MAP_ITEM_DATA, MapItemDataPacket.CODEC);
		this.registerPacket(BetaPackets.UPDATE_STAT, UpdateStatPacket.CODEC);
		this.registerPacket(BetaPackets.DISCONNECT, DisconnectPacket.CODEC);
	}
}
