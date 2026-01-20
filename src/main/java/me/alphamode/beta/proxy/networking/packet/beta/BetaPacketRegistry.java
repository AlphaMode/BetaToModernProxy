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
//		this.registerPacket(BetaPackets.SET_SPAWN_POSITION, SetSpawnPositionPacket::new);
//		this.registerPacket(BetaPackets.INTERACT, InteractPacket::new);
//		this.registerPacket(BetaPackets.SET_HEALTH, SetHealthPacket::new);
//		this.registerPacket(BetaPackets.PLAYER_CHANGE_DIMENSION, PlayerChangeDimensionPacket::new);
		this.registerPacket(BetaPackets.MOVE_PLAYER, MovePlayerPacket.CODEC);
		this.registerPacket(BetaPackets.MOVE_PLAYER_POS, MovePlayerPacket.Pos.CODEC);
		this.registerPacket(BetaPackets.MOVE_PLAYER_ROT, MovePlayerPacket.Rot::new);
		this.registerPacket(BetaPackets.MOVE_PLAYER_POS_ROT, MovePlayerPacket.PosRot.CODEC);
//		this.registerPacket(BetaPackets.PLAYER_ACTION, PlayerActionPacket::new);
//		this.registerPacket(BetaPackets.USE_ITEM, UseItemPacket::new);
//		this.registerPacket(BetaPackets.SET_CARRIED_ITEM, SetCarriedItemPacket::new);
//		this.registerPacket(BetaPackets.INTERACTION, InteractionPacket::new);
//		this.registerPacket(BetaPackets.ANIMATE, AnimatePacket::new);
//		this.registerPacket(BetaPackets.PLAYER_COMMAND, PlayerCommandPacket::new);
//		this.registerPacket(BetaPackets.ADD_PLAYER, AddPlayerPacket::new);
//		this.registerPacket(BetaPackets.ADD_ITEM_ENTITY, AddItemEntityPacket::new);
//		this.registerPacket(BetaPackets.TAKE_ITEM_ENTITY, TakeItemEntityPacket::new);
//		this.registerPacket(BetaPackets.ADD_ENTITY, AddEntityPacket::new);
//		this.registerPacket(BetaPackets.ADD_MOB, AddMobPacket::new);
//		this.registerPacket(BetaPackets.ADD_PAINTING, AddPaintingPacket::new);
//		this.registerPacket(BetaPackets.PLAYER_INPUT, PlayerInputPacket::new);
//		this.registerPacket(BetaPackets.SET_ENTITY_MOTION, SetEntityMotionPacket::new);
//		this.registerPacket(BetaPackets.REMOVE_ENTITY, RemoveEntityPacket::new);
//		this.registerPacket(BetaPackets.MOVE_ENTITY, MoveEntityPacket::new);
//		this.registerPacket(BetaPackets.MOVE_ENTITY_POS, MoveEntityPacket.Pos::new);
//		this.registerPacket(BetaPackets.MOVE_ENTITY_ROT, MoveEntityPacket.Rot::new);
//		this.registerPacket(BetaPackets.MOVE_ENTITY_POS_ROT, MoveEntityPacket.PosRot::new);
//		this.registerPacket(BetaPackets.TELEPORT_ENTITY, TeleportEntityPacket::new);
//		this.registerPacket(BetaPackets.ENTITY_EVENT, EntityEventPacket::new);
//		this.registerPacket(BetaPackets.SET_RIDING, SetRidingPacket::new);
//		this.registerPacket(BetaPackets.SET_ENTITY_DATA, SetEntityDataPacket::new);
		this.registerPacket(BetaPackets.CHUNK_VISIBILITY, ChunkVisibilityPacket.CODEC);
		this.registerPacket(BetaPackets.BLOCK_REGION_UPDATE, BlockRegionUpdatePacket.CODEC);
//		this.registerPacket(BetaPackets.CHUNK_TILES_UPDATE, ChunkTilesUpdatePacket::new);
//		this.registerPacket(BetaPackets.TILE_UPDATE, TileUpdatePacket::new);
//		this.registerPacket(BetaPackets.TILE_EVENT, TileEventPacket::new);
//		this.registerPacket(BetaPackets.EXPLODE, ExplodePacket::new);
//		this.registerPacket(BetaPackets.LEVEL_EVENT, LevelEventPacket::new);
//		this.registerPacket(BetaPackets.GAME_EVENT, GameEventPacket::new);
//		this.registerPacket(BetaPackets.ADD_GLOBAL_ENTITY, AddGlobalEntityPacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_OPEN, ContainerOpenPacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_CLOSE, ContainerClosePacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_CLICK, ContainerClickPacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_SET_SLOT, ContainerSetSlotPacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_SET_CONTENT, ContainerSetContentPacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_SET_DATA, ContainerSetDataPacket::new);
//		this.registerPacket(BetaPackets.CONTAINER_ACK, ContainerAckPacket::new);
//		this.registerPacket(BetaPackets.SIGN_UPDATE, SignUpdatePacket::new);
//		this.registerPacket(BetaPackets.MAP_ITEM_DATA, MapItemDataPacket::new);
//		this.registerPacket(BetaPackets.UPDATE_STAT, UpdateStatPacket::new);
//		this.registerPacket(BetaPackets.DISCONNECT, DisconnectPacket::new);
	}
}
