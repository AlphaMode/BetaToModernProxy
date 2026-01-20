package me.alphamode.beta.proxy.packet.beta;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.packet.beta.packets.*;
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

    public BetaPacketRegistry(int protocolVersion) {
        this.protocolVersion = protocolVersion;
        registerVanillaPackets();
    }

    @Override
    public Packet createPacket(int packetId, ByteBuf byteBuf) {
        final BetaPackets packetType = BetaPackets.getPacket(packetId);
        final Packet packet = this.registry.getOrDefault(packetType, () -> new UnknownPacket(packetId)).get();
        packet.read(byteBuf, this.protocolVersion);
        return packet;
    }

    @Override
    public int getPacketId(Packet packet) {
        if (packet instanceof UnknownPacket) {
            return ((UnknownPacket) packet).packetId;
        }

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

    private void registerPacket(int id, boolean clientbound, boolean serverbound, final Supplier<Packet> packetCreator) {
        registerPacket(BetaPackets.getPacket(id), packetCreator);
    }

    public void registerVanillaPackets() {
        registerPacket(0, true, true, KeepAlivePacket::new);
        registerPacket(1, true, true, LoginPacket::new);
        registerPacket(2, true, true, PreLoginPacket::new);
        registerPacket(3, true, true, ChatPacket::new);
        registerPacket(4, true, false, SetTimePacket::new);
        registerPacket(5, true, false, SetEquippedItemPacket::new);
        registerPacket(6, true, false, SetSpawnPositionPacket::new);
        registerPacket(7, false, true, InteractPacket::new);
        registerPacket(8, true, false, SetHealthPacket::new);
        registerPacket(9, true, true, PlayerChangeDimensionPacket::new);
        registerPacket(10, true, true, MovePlayerPacket::new);
        registerPacket(11, true, true, MovePlayerPacket.Pos::new);
        registerPacket(12, true, true, MovePlayerPacket.Rot::new);
        registerPacket(13, true, true, MovePlayerPacket.PosRot::new);
        registerPacket(14, false, true, PlayerActionPacket::new);
        registerPacket(15, false, true, UseItemPacket::new);
        registerPacket(16, false, true, SetCarriedItemPacket::new);
        registerPacket(17, true, false, InteractionPacket::new);
        registerPacket(18, true, true, AnimatePacket::new);
        registerPacket(19, false, true, PlayerCommandPacket::new);
        registerPacket(20, true, false, AddPlayerPacket::new);
        registerPacket(21, true, false, AddItemEntityPacket::new);
        registerPacket(22, true, false, TakeItemEntityPacket::new);
        registerPacket(23, true, false, AddEntityPacket::new);
        registerPacket(24, true, false, AddMobPacket::new);
        registerPacket(25, true, false, AddPaintingPacket::new);
        registerPacket(27, false, true, PlayerInputPacket::new);
        registerPacket(28, true, false, SetEntityMotionPacket::new);
        registerPacket(29, true, false, RemoveEntityPacket::new);
        registerPacket(30, true, false, MoveEntityPacket::new);
        registerPacket(31, true, false, MoveEntityPacket.Pos::new);
        registerPacket(32, true, false, MoveEntityPacket.Rot::new);
        registerPacket(33, true, false, MoveEntityPacket.PosRot::new);
        registerPacket(34, true, false, TeleportEntityPacket::new);
        registerPacket(38, true, false, EntityEventPacket::new);
        registerPacket(39, true, false, SetRidingPacket::new);
        registerPacket(40, true, false, SetEntityDataPacket::new);
        registerPacket(50, true, false, ChunkVisibilityPacket::new);
        registerPacket(51, true, false, BlockRegionUpdatePacket::new);
        registerPacket(52, true, false, ChunkTilesUpdatePacket::new);
        registerPacket(53, true, false, TileUpdatePacket::new);
        registerPacket(54, true, false, TileEventPacket::new);
        registerPacket(60, true, false, ExplodePacket::new);
        registerPacket(61, true, false, LevelEventPacket::new);
        registerPacket(70, true, false, GameEventPacket::new);
        registerPacket(71, true, false, AddGlobalEntityPacket::new);
//        registerPacket(100, true, false, ContainerOpenPacket::new);
        registerPacket(101, true, true, ContainerClosePacket::new);
        registerPacket(102, false, true, ContainerClickPacket::new);
        registerPacket(103, true, false, ContainerSetSlotPacket::new);
        registerPacket(104, true, false, ContainerSetContentPacket::new);
        registerPacket(105, true, false, ContainerSetDataPacket::new);
        registerPacket(106, true, true, ContainerAckPacket::new);
        registerPacket(130, true, true, SignUpdatePacket::new);
        registerPacket(131, true, false, MapItemDataPacket::new);
        registerPacket(200, true, false, UpdateStatPacket::new);
        registerPacket(255, true, true, DisconnectPacket::new);
    }
}
