package me.alphamode.beta.proxy.networking.packet.beta.enums;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public enum BetaPackets implements Packets {
	KEEP_ALIVE(0, KeepAlivePacket.CODEC),
	LOGIN(1, LoginPacket.CODEC),
	HANDSHAKE(2, HandshakePacket.CODEC),
	CHAT(3, ChatPacket.CODEC),
	SET_TIME(4, SetTimePacket.CODEC),
	SET_EQUIPPED_ITEM(5, SetEquippedItemPacket.CODEC),
	SET_SPAWN_POSITION(6, SetSpawnPositionPacket.CODEC),
	INTERACT(7, InteractPacket.CODEC),
	SET_HEALTH(8, SetHealthPacket.CODEC),
	PLAYER_CHANGE_DIMENSION(9, PlayerChangeDimensionPacket.CODEC),
	MOVE_PLAYER(10, MovePlayerPacket.StatusOnly.CODEC),
	MOVE_PLAYER_POS(11, MovePlayerPacket.Pos.CODEC),
	MOVE_PLAYER_ROT(12, MovePlayerPacket.Rot.CODEC),
	MOVE_PLAYER_POS_ROT(13, MovePlayerPacket.PosRot.CODEC),
	PLAYER_ACTION(14, PlayerActionPacket.CODEC),
	USE_ITEM(15, UseItemPacket.CODEC),
	SET_CARRIED_ITEM(16, SetCarriedItemPacket.CODEC),
	INTERACTION(17, InteractionPacket.CODEC),
	ANIMATE(18, AnimatePacket.CODEC),
	PLAYER_COMMAND(19, PlayerCommandPacket.CODEC),
	ADD_PLAYER(20, AddPlayerPacket.CODEC),
	ADD_ITEM_ENTITY(21, AddItemEntityPacket.CODEC),
	TAKE_ITEM_ENTITY(22, TakeItemEntityPacket.CODEC),
	ADD_ENTITY(23, AddEntityPacket.CODEC),
	ADD_MOB(24, AddMobPacket.CODEC),
	ADD_PAINTING(25, AddPaintingPacket.CODEC),
	PLAYER_INPUT(27, PlayerInputPacket.CODEC),
	SET_ENTITY_MOTION(28, SetEntityMotionPacket.CODEC),
	REMOVE_ENTITY(29, RemoveEntityPacket.CODEC),
	MOVE_ENTITY(30, MoveEntityPacket.CODEC),
	MOVE_ENTITY_POS(31, MoveEntityPacket.Pos.CODEC),
	MOVE_ENTITY_ROT(32, MoveEntityPacket.Rot.CODEC),
	MOVE_ENTITY_POS_ROT(33, MoveEntityPacket.PosRot.CODEC),
	TELEPORT_ENTITY(34, TeleportEntityPacket.CODEC),
	ENTITY_EVENT(38, EntityEventPacket.CODEC),
	SET_RIDING(39, SetRidingPacket.CODEC),
	SET_ENTITY_DATA(40, SetEntityDataPacket.CODEC),
	CHUNK_VISIBILITY(50, ChunkVisibilityPacket.CODEC),
	BLOCK_REGION_UPDATE(51, BlockRegionUpdatePacket.CODEC),
	CHUNK_TILES_UPDATE(52, ChunkTilesUpdatePacket.CODEC),
	TILE_UPDATE(53, TileUpdatePacket.CODEC),
	TILE_EVENT(54, TileEventPacket.CODEC),
	EXPLODE(60, ExplodePacket.CODEC),
	LEVEL_EVENT(61, LevelEventPacket.CODEC),
	GAME_EVENT(70, GameEventPacket.CODEC),
	ADD_GLOBAL_ENTITY(71, AddGlobalEntityPacket.CODEC),
	CONTAINER_OPEN(100, ContainerOpenPacket.CODEC),
	CONTAINER_CLOSE(101, ContainerClosePacket.CODEC),
	CONTAINER_CLICK(102, ContainerClickPacket.CODEC),
	CONTAINER_SET_SLOT(103, ContainerSetSlotPacket.CODEC),
	CONTAINER_SET_CONTENT(104, ContainerSetContentPacket.CODEC),
	CONTAINER_SET_DATA(105, ContainerSetDataPacket.CODEC),
	CONTAINER_ACK(106, ContainerAckPacket.CODEC),
	SIGN_UPDATE(130, SignUpdatePacket.CODEC),
	MAP_ITEM_DATA(131, MapItemDataPacket.CODEC),
	UPDATE_STAT(200, UpdateStatPacket.CODEC),
	SERVER_LIST_PING(254, ServerListPingPacket.CODEC),
	DISCONNECT(255, DisconnectPacket.CODEC);

	private final int id;
	private final StreamCodec<ByteBuf, ? extends BetaPacket> codec;

	BetaPackets(final int id, final StreamCodec<ByteBuf, ? extends BetaPacket> codec) {
		this.id = id;
		this.codec = codec;
	}

	public int getId() {
		return this.id;
	}

	public <T extends BetaPacket> StreamCodec<ByteBuf, T> codec() {
		return (StreamCodec<ByteBuf, T>) this.codec;
	}

	public static BetaPackets getPacket(final int id) {
		for (final BetaPackets packet : BetaPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
