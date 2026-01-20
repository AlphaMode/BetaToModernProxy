package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.beta.packets.*;
import net.raphimc.netminecraft.constants.ConnectionState;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.packet.UnknownPacket;
import net.raphimc.netminecraft.packet.registry.PacketRegistry;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class BetaPacketRegistry implements PacketRegistry {
	public static final BetaPacketRegistry INSTANCE = new BetaPacketRegistry(14);
	private final Map<BetaPackets, Supplier<Packet>> registry = new EnumMap<>(BetaPackets.class);
	private final Map<Class<?>, BetaPackets> reverseRegistry = new HashMap<>();
	private ConnectionState connectionState = ConnectionState.HANDSHAKING;
	private final int protocolVersion;

	public BetaPacketRegistry(final int protocolVersion) {
		this.protocolVersion = protocolVersion;
		this.registerVanillaPackets();
	}

	@Override
	public Packet createPacket(final int packetId, final ByteBuf byteBuf) {
		final BetaPackets packetType = BetaPackets.getPacket(packetId);
		final Packet packet = this.registry.getOrDefault(packetType, () -> new UnknownPacket(packetId)).get();

		final int idx = byteBuf.readerIndex();
		packet.read(byteBuf, this.protocolVersion);
		IO.println("Reading packet " + (packetType == null ? "Unknown (" + packetId + ")" : packetType) + " of size " + (idx + byteBuf.readerIndex())); // Don't know if size is correct here

		return packet;
	}

	@Override
	public int getPacketId(final Packet packet) {
		if (packet instanceof UnknownPacket unknownPacket) {
			return unknownPacket.packetId;
		} else {
			final Class<?> packetClass = packet.getClass();
			if (!this.reverseRegistry.containsKey(packetClass)) {
				throw new IllegalArgumentException("Packet " + packetClass.getSimpleName() + " is not registered in the packet registry");
			}

			final int packetId = this.reverseRegistry.get(packetClass).getId();
			if (packetId == -1) {
				throw new IllegalArgumentException("Packet " + packetClass.getSimpleName() + " is not available for protocol version " + "b1.7.3");
			}

			return packetId;
		}
	}

	public Packet getPacket(final int packetId) {
		final BetaPackets packetType = BetaPackets.getPacket(packetId);
		return this.registry.getOrDefault(packetType, () -> new UnknownPacket(packetId)).get();
	}

	@Override
	public int getProtocolVersion() {
		return this.protocolVersion;
	}

	@Override
	public ConnectionState getConnectionState() {
		return connectionState;
	}

	@Override
	public void setConnectionState(ConnectionState connectionState) {
		this.connectionState = connectionState;
	}

	protected final void registerPacket(final BetaPackets packetType, final Supplier<Packet> packetCreator) {
		this.unregisterPacket(packetType);
		final Class<?> packetClass = packetCreator.get().getClass();
		this.registry.put(packetType, packetCreator);
		if (this.reverseRegistry.put(packetClass, packetType) != null) {
			throw new IllegalArgumentException("Packet " + packetClass.getSimpleName() + " is already registered in the packet registry");
		}
	}

	protected final void unregisterPacket(final BetaPackets packetType) {
		this.registry.remove(packetType);
		this.reverseRegistry.values().removeIf(packetType::equals);
	}

	public void registerVanillaPackets() {
		registerPacket(BetaPackets.KEEP_ALIVE, KeepAlivePacket::new);
		registerPacket(BetaPackets.LOGIN, LoginPacket::new);
		registerPacket(BetaPackets.HANDSHAKE, HandshakePacket::new);
		registerPacket(BetaPackets.CHAT, ChatPacket::new);
		registerPacket(BetaPackets.SET_TIME, SetTimePacket::new);
		registerPacket(BetaPackets.SET_EQUIPPED_ITEM, SetEquippedItemPacket::new);
		registerPacket(BetaPackets.SET_SPAWN_POSITION, SetSpawnPositionPacket::new);
		registerPacket(BetaPackets.INTERACT, InteractPacket::new);
		registerPacket(BetaPackets.SET_HEALTH, SetHealthPacket::new);
		registerPacket(BetaPackets.PLAYER_CHANGE_DIMENSION, PlayerChangeDimensionPacket::new);
		registerPacket(BetaPackets.MOVE_PLAYER, MovePlayerPacket::new);
		registerPacket(BetaPackets.MOVE_PLAYER_POS, MovePlayerPacket.Pos::new);
		registerPacket(BetaPackets.MOVE_PLAYER_ROT, MovePlayerPacket.Rot::new);
		registerPacket(BetaPackets.MOVE_PLAYER_POS_ROT, MovePlayerPacket.PosRot::new);
		registerPacket(BetaPackets.PLAYER_ACTION, PlayerActionPacket::new);
		registerPacket(BetaPackets.USE_ITEM, UseItemPacket::new);
		registerPacket(BetaPackets.SET_CARRIED_ITEM, SetCarriedItemPacket::new);
		registerPacket(BetaPackets.INTERACTION, InteractionPacket::new);
		registerPacket(BetaPackets.ANIMATE, AnimatePacket::new);
		registerPacket(BetaPackets.PLAYER_COMMAND, PlayerCommandPacket::new);
		registerPacket(BetaPackets.ADD_PLAYER, AddPlayerPacket::new);
		registerPacket(BetaPackets.ADD_ITEM_ENTITY, AddItemEntityPacket::new);
		registerPacket(BetaPackets.TAKE_ITEM_ENTITY, TakeItemEntityPacket::new);
		registerPacket(BetaPackets.ADD_ENTITY, AddEntityPacket::new);
		registerPacket(BetaPackets.ADD_MOB, AddMobPacket::new);
		registerPacket(BetaPackets.ADD_PAINTING, AddPaintingPacket::new);
		registerPacket(BetaPackets.PLAYER_INPUT, PlayerInputPacket::new);
		registerPacket(BetaPackets.SET_ENTITY_MOTION, SetEntityMotionPacket::new);
		registerPacket(BetaPackets.REMOVE_ENTITY, RemoveEntityPacket::new);
		registerPacket(BetaPackets.MOVE_ENTITY, MoveEntityPacket::new);
		registerPacket(BetaPackets.MOVE_ENTITY_POS, MoveEntityPacket.Pos::new);
		registerPacket(BetaPackets.MOVE_ENTITY_ROT, MoveEntityPacket.Rot::new);
		registerPacket(BetaPackets.MOVE_ENTITY_POS_ROT, MoveEntityPacket.PosRot::new);
		registerPacket(BetaPackets.TELEPORT_ENTITY, TeleportEntityPacket::new);
		registerPacket(BetaPackets.ENTITY_EVENT, EntityEventPacket::new);
		registerPacket(BetaPackets.SET_RIDING, SetRidingPacket::new);
		registerPacket(BetaPackets.SET_ENTITY_DATA, SetEntityDataPacket::new);
		registerPacket(BetaPackets.CHUNK_VISIBILITY, ChunkVisibilityPacket::new);
		registerPacket(BetaPackets.BLOCK_REGION_UPDATE, BlockRegionUpdatePacket::new);
		registerPacket(BetaPackets.CHUNK_TILES_UPDATE, ChunkTilesUpdatePacket::new);
		registerPacket(BetaPackets.TILE_UPDATE, TileUpdatePacket::new);
		registerPacket(BetaPackets.TILE_EVENT, TileEventPacket::new);
		registerPacket(BetaPackets.EXPLODE, ExplodePacket::new);
		registerPacket(BetaPackets.LEVEL_EVENT, LevelEventPacket::new);
		registerPacket(BetaPackets.GAME_EVENT, GameEventPacket::new);
		registerPacket(BetaPackets.ADD_GLOBAL_ENTITY, AddGlobalEntityPacket::new);
		registerPacket(BetaPackets.CONTAINER_OPEN, ContainerOpenPacket::new);
		registerPacket(BetaPackets.CONTAINER_CLOSE, ContainerClosePacket::new);
		registerPacket(BetaPackets.CONTAINER_CLICK, ContainerClickPacket::new);
		registerPacket(BetaPackets.CONTAINER_SET_SLOT, ContainerSetSlotPacket::new);
		registerPacket(BetaPackets.CONTAINER_SET_CONTENT, ContainerSetContentPacket::new);
		registerPacket(BetaPackets.CONTAINER_SET_DATA, ContainerSetDataPacket::new);
		registerPacket(BetaPackets.CONTAINER_ACK, ContainerAckPacket::new);
		registerPacket(BetaPackets.SIGN_UPDATE, SignUpdatePacket::new);
		registerPacket(BetaPackets.MAP_ITEM_DATA, MapItemDataPacket::new);
		registerPacket(BetaPackets.UPDATE_STAT, UpdateStatPacket::new);
		registerPacket(BetaPackets.DISCONNECT, DisconnectPacket::new);
	}
}
