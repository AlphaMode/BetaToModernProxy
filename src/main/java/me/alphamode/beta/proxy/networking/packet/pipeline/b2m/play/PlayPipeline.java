package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.ServerConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.*;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.util.ChunkTranslator;
import me.alphamode.beta.proxy.util.ItemTranslator;
import me.alphamode.beta.proxy.util.data.BlockHitResult;
import me.alphamode.beta.proxy.util.data.ChunkPos;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.BetaItemStack;
import me.alphamode.beta.proxy.util.data.modern.*;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import me.alphamode.beta.proxy.util.data.modern.enums.InteractionHand;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLevelChunkPacketData;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLightUpdatePacketData;
import me.alphamode.beta.proxy.util.data.modern.registry.dimension.Dimension;
import net.lenni0451.mcstructs.text.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class PlayPipeline {
	private static final Logger LOGGER = LogManager.getLogger(PlayPipeline.class);
	public static final PacketPipeline<PlayPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<PlayPipeline>builder()
			.clientHandler(C2SPlayKeepAlivePacket.class, PlayPipeline::handleC2SKeepAlive)
			.serverHandler(SetSpawnPositionPacket.class, PlayPipeline::handleS2CSetSpawnPosition)
			.clientHandler(C2SConfigurationAcknowledgedPacket.class, PlayPipeline::handleC2SConfigurationAcknowledged)
			.serverHandler(SetTimePacket.class, PlayPipeline::handleS2CSetTime)
			.serverHandler(SetHealthPacket.class, PlayPipeline::handleS2CSetHealth)
			.serverHandler(AddEntityPacket.class, PlayPipeline::handleS2CAddEntity)
			.serverHandler(AddPlayerPacket.class, PlayPipeline::handleS2CAddPlayer)
			.clientHandler(C2SSwingPacket.class, PlayPipeline::handleC2SSwing)
			.serverHandler(AnimatePacket.class, PlayPipeline::handleS2CAnimate)
			.serverHandler(GameEventPacket.class, PlayPipeline::handleS2CGameEvent)
			.clientHandler(C2SUseItemPacket.class, PlayPipeline::handleC2SUseItem)
			.clientHandler(C2SUseItemOnPacket.class, PlayPipeline::handleC2SUseItemOn)
			.clientHandler(C2SClientCommandPacket.class, PlayPipeline::handleC2SClientCommand)
			.serverHandler(ChatPacket.class, PlayPipeline::handleS2CChat)
			.clientHandler(C2SChatPacket.class, PlayPipeline::handleC2SChat)
			.clientHandler(C2SChatCommandPacket.class, PlayPipeline::handleC2SChatCommand)
			.clientHandler(C2SClientTickEndPacket.class, PlayPipeline::handleC2STickEnd)
//            .clientHandler(S2CPlayerPositionPacket.class, PlayPipeline::handleS2CMovePlayer)
			.serverHandler(MovePlayerPacket.class, PlayPipeline::handleBetaMovePlayer)
			.clientHandler(C2SMovePlayerPacket.class, PlayPipeline::handleC2SMovePlayerPos)
			.serverHandler(SetEntityMotionPacket.class, PlayPipeline::handleS2CSetEntityMotion)
			.serverHandler(ChunkVisibilityPacket.class, PlayPipeline::handleS2CChunkVisibility)
			.serverHandler(BlockRegionUpdatePacket.class, PlayPipeline::handleBlockRegionUpdate)
			.serverHandler(SetCarriedItemPacket.class, PlayPipeline::handleS2CSetCarriedItem)
			.clientHandler(C2SSetCarriedItemPacket.class, PlayPipeline::handleC2SSetCarriedItem)
			.clientHandler(C2SContainerSlotStateChangedPacket.class, PlayPipeline::handleC2SContainerSlotStateChanged)
			.serverHandler(ContainerSetSlotPacket.class, PlayPipeline::handleS2CContainerSetSlot)
			.serverHandler(ContainerSetContentPacket.class, PlayPipeline::handleS2CContainerSetContent)
			.serverHandler(ContainerSetDataPacket.class, PlayPipeline::handleS2CContainerSetData)
			.serverHandler(ContainerClosePacket.class, PlayPipeline::handleS2CContainerClose)
			.clientHandler(C2SContainerClosePacket.class, PlayPipeline::handleC2SContainerClose)
			.serverHandler(DisconnectPacket.class, PlayPipeline::handleS2CDisconnect)
			// there is no C2SDisconnect packet?
			.unhandledClient(PlayPipeline::passClientToNextPipeline)
			.unhandledServer(PlayPipeline::passServerToNextPipeline)
			.build();

	public void handleC2SKeepAlive(final ClientConnection connection, final C2SPlayKeepAlivePacket packet) {
		connection.getServerConnection().send(new KeepAlivePacket());
		connection.setLastKeepAliveId(packet.id());
	}

	/**
	 * Dear AlphaMode
	 * This code is just for fun/to try and get into the world, it is most likely not accurate to what you are wanting to do
	 * Sincerely, lowercase of the btw
	 */
	public void handleS2CSetSpawnPosition(final ClientConnection connection, final SetSpawnPositionPacket packet) {
//		TODO: S2CRecipeBookAddPacket

		connection.send(new S2CPlayLoginPacket(
				0, // TODO
				false,
				List.of(Dimension.OVERWORLD, Dimension.NETHER, Dimension.SKY),
				BrodernProxy.getProxy().config().getMaxPlayers(),
				16,
				16,
				false,
				false,
				false,
				new CommonPlayerSpawnInfo(
						null, // TODO (Holder<DimensionType>)
						Dimension.byLegacyId(0),
						0,
						GameMode.SURVIVAL,
						GameMode.SURVIVAL,
						false,
						false,
						Optional.empty(),
						300,
						63
				),
				false
		));

	/*	connection.send(new S2CPlayerPositionPacket(
				0, // TODO
				new PositionMoveRotation(new Vec3d(0, 63, 0), new Vec3d(0, 0, 0), 0.0F, 0.0F),
				Set.of()
		));*/

//		TODO: S2CInitializeBorderPacket
		connection.send(new S2CSetDefaultSpawnPositionPacket(new LevelData.RespawnData(
				GlobalPos.of(Dimension.OVERWORLD, packet.position().toBlockPos()),
				0.0F, 0.0F
		)));

		connection.send(new S2CGameEventPacket(S2CGameEventPacket.LEVEL_CHUNKS_LOAD_START, 0));
		connection.send(new S2CSetChunkCacheRadiusPacket(0));
		connection.send(new S2CSetChunkCacheCenterPacket(0, 0));
		connection.send(new S2CLevelChunkWithLightPacket(
				0, 0,
				new ClientboundLevelChunkPacketData(
						Map.of(),
						new byte[1024],
						List.of()
				),
				new ClientboundLightUpdatePacketData(
						new BitSet(1024),
						new BitSet(1024),
						new BitSet(1024),
						new BitSet(1024),
						List.of(),
						List.of()
				)
		));
	}

	public void handleC2SConfigurationAcknowledged(final ClientConnection connection, final C2SConfigurationAcknowledgedPacket packet) {
	}

	public void handleS2CSetTime(final ClientConnection connection, final SetTimePacket packet) {
		// TODO/FIX
		// connection.send(new S2CSetTimePacket(packet.id(), packet.id(), true));
	}

	public void handleS2CSetHealth(final ClientConnection connection, final SetHealthPacket packet) {
		final float health = Math.max(0, packet.health());
		connection.send(new S2CSetHealthPacket(health));
		if (health == 0) {
			connection.send(new S2CGameEventPacket(S2CGameEventPacket.IMMEDIATE_RESPAWN, 0));
		}
	}

	public void handleS2CAddEntity(final ClientConnection connection, final AddEntityPacket packet) {
		connection.send(new S2CAddEntityPacket(
				packet.entityId(),
				UUID.randomUUID(),
				packet.type(),
				packet.position().toVec3d(),
				new Vec3d(packet.xd(), packet.yd(), packet.zd()),
				(byte) 0,
				(byte) 0,
				(byte) 0,
				0
		));
	}


	public void handleS2CAddPlayer(final ClientConnection connection, final AddPlayerPacket packet) {
		// Server attempted to add player prior to sending player info (Player id 21586c2d-49e7-40a8-a485-db85537d3c2b)
		connection.send(new S2CAddEntityPacket(
				packet.entityId(),
				UUID.randomUUID(),
				155,
				packet.position().toVec3d(),
				Vec3d.ZERO,
				packet.pitch(),
				packet.yaw(),
				(byte) 0,
				0
		));
	}

	public void handleC2SSwing(final ClientConnection connection, final C2SSwingPacket packet) {
		if (packet.hand() == InteractionHand.MAIN_HAND) {
			connection.getServerConnection().send(new AnimatePacket(0, AnimatePacket.SWING_ARM));
		}
	}

	public void handleS2CAnimate(final ClientConnection connection, final AnimatePacket packet) {
		switch (packet.action()) {
			case AnimatePacket.SWING_ARM ->
					connection.send(new S2CAnimatePacket(packet.entityId(), S2CAnimatePacket.SWING_MAIN_HAND));

			case AnimatePacket.DAMAGE_ANIMATION -> {
				// TODO
			}

			case AnimatePacket.LEAVE_BED ->
					connection.send(new S2CAnimatePacket(packet.entityId(), S2CAnimatePacket.WAKE_UP));
		}
	}

	public void handleS2CGameEvent(final ClientConnection connection, final GameEventPacket packet) {
		if (packet.type() == GameEventPacket.Type.INVALID_BED) {
			connection.send(new S2CSystemChatPacket(TextComponent.translation("block.minecraft.spawn.not_valid"), false));
		} else if (packet.type() == GameEventPacket.Type.BEGIN_RAINING) {
			connection.send(new S2CGameEventPacket(S2CGameEventPacket.START_RAINING, 0.0F));
		} else if (packet.type() == GameEventPacket.Type.END_RAINING) {
			connection.send(new S2CGameEventPacket(S2CGameEventPacket.STOP_RAINING, 0.0F));
		}
	}

	public void handleC2SUseItem(final ClientConnection connection, final C2SUseItemPacket packet) {
		connection.getServerConnection().send(new UseItemPacket(
				new Vec3i(-1, -1, -1),
				(byte) 0,
				BetaItemStack.EMPTY
		));
	}

	public void handleC2SUseItemOn(final ClientConnection connection, final C2SUseItemOnPacket packet) {
		final BlockHitResult result = packet.hitResult();
		connection.getServerConnection().send(new UseItemPacket(
				new Vec3i(result.getBlockPos().x(), result.getBlockPos().y(), result.getBlockPos().z()),
				(byte) result.getDirection().ordinal(),
				BetaItemStack.EMPTY
		));
	}

	public void handleC2SClientCommand(final ClientConnection connection, final C2SClientCommandPacket packet) {
		if (packet.action() == C2SClientCommandPacket.Action.PERFORM_RESPAWN) {
			connection.getServerConnection().send(new PlayerChangeDimensionPacket((byte) 0));
		}
	}

	public void handleS2CChat(final ClientConnection connection, final ChatPacket packet) {
		final String message = packet.message();
		LOGGER.info("{}", message);
		connection.send(new S2CSystemChatPacket(TextComponent.of(message), false));
	}

	public void handleC2SChat(final ClientConnection connection, final C2SChatPacket packet) {
		connection.getServerConnection().send(new ChatPacket(packet.message()));
	}

	public void handleC2SChatCommand(final ClientConnection connection, final C2SChatCommandPacket packet) {
		connection.getServerConnection().send(new ChatPacket("/" + packet.command()));
	}

	public void handleS2CChunkVisibility(final ClientConnection connection, final ChunkVisibilityPacket packet) {
		if (!packet.visible()) {
			connection.send(new S2CForgetLevelChunkPacket(new ChunkPos(packet.x(), packet.z())));
		}
	}

	public void handleBlockRegionUpdate(final ClientConnection connection, final BlockRegionUpdatePacket packet) {
		final byte[] buffer = new byte[packet.xs() * packet.ys() * packet.zs() * 5 / 2];
		try (final Inflater inflater = new Inflater()) {
			inflater.setInput(packet.data());
			inflater.inflate(buffer);
		} catch (final DataFormatException exception) {
			connection.kick("Bad compressed data format");
			return;
		}

		connection.send(new S2CSetChunkCacheRadiusPacket(2048));
		connection.send(new S2CSetChunkCacheCenterPacket(packet.x(), packet.z()));
		ChunkTranslator.readBetaRegionData(connection, packet.x(), packet.y(), packet.z(), packet.xs() + 1, packet.ys() + 1, packet.zs() + 1, buffer);
	}

	public void handleS2CSetCarriedItem(final ClientConnection connection, final SetCarriedItemPacket packet) {
		connection.send(new C2SSetCarriedItemPacket(packet.slot()));
	}

	public void handleC2SSetCarriedItem(final ClientConnection connection, final C2SSetCarriedItemPacket packet) {
		connection.getServerConnection().send(new SetCarriedItemPacket(packet.slot()));
	}

	public void handleC2STickEnd(final ClientConnection connection, final C2SClientTickEndPacket packet) {
		connection.tick();
	}

	// TODO: double check accuracy

	public void handleBetaMovePlayer(final ClientConnection connection, final MovePlayerPacket packet) {
		connection.send(new S2CPlayerPositionPacket(0, new PositionMoveRotation(new Vec3d(packet.x(), packet.y(), packet.z()), Vec3d.ZERO, packet.yRot(), packet.xRot()), Collections.emptySet()));
		connection.getServerConnection().send(packet);
	}

	public void handleC2SMovePlayerPos(final ClientConnection connection, final C2SMovePlayerPacket packet) {
		final ServerConnection serverConnection = connection.getServerConnection();
		switch (packet) {
			case C2SMovePlayerPacket.Pos p ->
					serverConnection.send(new MovePlayerPacket.Pos(p.x(), p.y(), p.y() + 1.62F, p.z(), p.onGround()));
			case C2SMovePlayerPacket.Rot p ->
					serverConnection.send(new MovePlayerPacket.Rot(p.yRot(), p.xRot(), p.onGround()));
			case C2SMovePlayerPacket.PosRot p ->
					serverConnection.send(new MovePlayerPacket.PosRot(p.x(), p.y(), p.y() + 1.62F, p.z(), p.yRot(), p.xRot(), p.onGround()));
			case C2SMovePlayerPacket.StatusOnly p -> serverConnection.send(new MovePlayerPacket.Status(p.onGround()));
		}
	}

	public void handleS2CSetEntityMotion(final ClientConnection connection, final SetEntityMotionPacket packet) {
		connection.send(new S2CSetEntityMotionPacket(packet.id(), new Vec3d(packet.deltaX(), packet.deltaY(), packet.deltaZ())));
	}

	public void handleC2SContainerSlotStateChanged(final ClientConnection connection, final C2SContainerSlotStateChangedPacket packet) {
	}

	public void handleS2CContainerSetSlot(final ClientConnection connection, final ContainerSetSlotPacket packet) {
		connection.send(new S2CContainerSetSlotPacket(packet.containerId(), 0, packet.slot(), ItemTranslator.toModernStack(packet.item())));
	}

	public void handleS2CContainerSetContent(final ClientConnection connection, final ContainerSetContentPacket packet) {
		connection.send(new S2CContainerSetContentPacket(
				packet.containerId(),
				0,
				Arrays.stream(packet.items()).map(ItemTranslator::toModernStack).toList(),
				ModernItemStack.EMPTY
		));
		LOGGER.warn("Sending container content");
	}

	public void handleS2CContainerSetData(final ClientConnection connection, final ContainerSetDataPacket packet) {
		connection.send(new S2CContainerSetDataPacket(packet.containerId(), packet.id(), packet.value()));
	}

	public void handleS2CContainerClose(final ClientConnection connection, final ContainerClosePacket packet) {
		connection.send(new S2CContainerClosePacket(packet.containerId()));
	}

	public void handleC2SContainerClose(final ClientConnection connection, final C2SContainerClosePacket packet) {
		connection.getServerConnection().send(new ContainerClosePacket((byte) packet.containerId()));
	}

	public void handleS2CDisconnect(final ClientConnection connection, final DisconnectPacket packet) {
		connection.kick(packet.reason());
	}

	public void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	public void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
