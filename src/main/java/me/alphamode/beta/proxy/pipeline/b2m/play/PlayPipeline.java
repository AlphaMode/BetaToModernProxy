package me.alphamode.beta.proxy.pipeline.b2m.play;

import com.mojang.authlib.GameProfile;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.entity.Player;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.ServerConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.*;
import me.alphamode.beta.proxy.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.util.ChunkTranslator;
import me.alphamode.beta.proxy.util.ItemTranslator;
import me.alphamode.beta.proxy.util.data.BlockHitResult;
import me.alphamode.beta.proxy.util.data.ChunkPos;
import me.alphamode.beta.proxy.util.data.Vec3d;
import me.alphamode.beta.proxy.util.data.Vec3i;
import me.alphamode.beta.proxy.util.data.beta.BetaEntityType;
import me.alphamode.beta.proxy.util.data.beta.item.BetaItemStack;
import me.alphamode.beta.proxy.util.data.modern.*;
import me.alphamode.beta.proxy.util.data.modern.components.DataComponentPatch;
import me.alphamode.beta.proxy.util.data.modern.entity.EntityDataSerializers;
import me.alphamode.beta.proxy.util.data.modern.entity.ModernSynchedEntityData;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import me.alphamode.beta.proxy.util.data.modern.enums.InteractionHand;
import me.alphamode.beta.proxy.util.data.modern.item.HashedPatchMap;
import me.alphamode.beta.proxy.util.data.modern.item.HashedStack;
import me.alphamode.beta.proxy.util.data.modern.item.ModernItemStack;
import me.alphamode.beta.proxy.util.data.modern.registry.dimension.BetaDimension;
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
			.serverHandler(AddMobPacket.class, PlayPipeline::handleS2CAddMob)
			.serverHandler(AddEntityPacket.class, PlayPipeline::handleS2CAddEntity)
			.serverHandler(AddPlayerPacket.class, PlayPipeline::handleS2CAddPlayer)
			.serverHandler(AddItemEntityPacket.class, PlayPipeline::handleS2CAddItemEntity)
			.clientHandler(C2SSwingPacket.class, PlayPipeline::handleC2SSwing)
			.serverHandler(AnimatePacket.class, PlayPipeline::handleS2CAnimate)
			.serverHandler(GameEventPacket.class, PlayPipeline::handleS2CGameEvent)
			.clientHandler(C2SInteractPacket.class, PlayPipeline::handleC2SInteract)
			.clientHandler(C2SUseItemPacket.class, PlayPipeline::handleC2SUseItem)
			.clientHandler(C2SUseItemOnPacket.class, PlayPipeline::handleC2SUseItemOn)
			.clientHandler(C2SClientCommandPacket.class, PlayPipeline::handleC2SClientCommand)
			.serverHandler(ChatPacket.class, PlayPipeline::handleS2CChat)
			.clientHandler(C2SChatPacket.class, PlayPipeline::handleC2SChat)
			.clientHandler(C2SChatCommandPacket.class, PlayPipeline::handleC2SChatCommand)
			.clientHandler(C2SClientTickEndPacket.class, PlayPipeline::handleC2STickEnd)
			.serverHandler(MovePlayerPacket.class, PlayPipeline::handleBetaMovePlayer)
			.clientHandler(C2SMovePlayerPacket.class, PlayPipeline::handleC2SMovePlayerPos)
			.serverHandler(SetEntityMotionPacket.class, PlayPipeline::handleS2CSetEntityMotion)
			.clientHandler(C2SPlayerInputPacket.class, PlayPipeline::handleC2SPlayerInput)
			.serverHandler(ChunkVisibilityPacket.class, PlayPipeline::handleS2CChunkVisibility)
			.serverHandler(BlockRegionUpdatePacket.class, PlayPipeline::handleBlockRegionUpdate)
			.serverHandler(SetCarriedItemPacket.class, PlayPipeline::handleS2CSetCarriedItem)
			.clientHandler(C2SSetCarriedItemPacket.class, PlayPipeline::handleC2SSetCarriedItem)
			.clientHandler(C2SContainerSlotStateChangedPacket.class, PlayPipeline::handleC2SContainerSlotStateChanged)
			.clientHandler(C2SContainerClickPacket.class, PlayPipeline::handleC2SC2SContainerClick)
			.serverHandler(ContainerSetSlotPacket.class, PlayPipeline::handleS2CContainerSetSlot)
			.serverHandler(ContainerSetContentPacket.class, PlayPipeline::handleS2CContainerSetContent)
			.serverHandler(ContainerSetDataPacket.class, PlayPipeline::handleS2CContainerSetData)
			.serverHandler(ContainerAckPacket.class, PlayPipeline::handleS2CContainerAck)
			.serverHandler(ContainerOpenPacket.class, PlayPipeline::handleS2CContainerOpen)
			.serverHandler(ContainerClosePacket.class, PlayPipeline::handleS2CContainerClose)
			.clientHandler(C2SContainerClosePacket.class, PlayPipeline::handleC2SContainerClose)
			.clientHandler(C2SPlayerActionPacket.class, PlayPipeline::handleC2SPlayerAction)
			.serverHandler(DisconnectPacket.class, PlayPipeline::handleS2CDisconnect)
			// there is no C2SDisconnect packet?
			.unhandledClient(PlayPipeline::passClientToNextPipeline)
			.unhandledServer(PlayPipeline::passServerToNextPipeline)
			.build();

	protected final Player player;
	protected long seed;
	protected short lastUid = 0;

	public PlayPipeline(final Player player, final long seed) {
		this.player = Objects.requireNonNull(player, "Can't construct play pipeline without player");
		this.seed = seed;
	}

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
		connection.send(new S2CPlayLoginPacket(
				this.player.getId(),
				false,
				List.of(BetaDimension.OVERWORLD, BetaDimension.NETHER, BetaDimension.SKY),
				BrodernProxy.getProxy().config().getMaxPlayers(),
				16,
				16,
				false,
				false,
				false,
				new CommonPlayerSpawnInfo(
						null, // TODO (Holder<DimensionType>)
						BetaDimension.byLegacyId(this.player.getDimension()),
						this.seed,
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

//		TODO: S2CInitializeBorderPacket
		connection.send(new S2CSetDefaultSpawnPositionPacket(new LevelData.RespawnData(
				GlobalPos.of(BetaDimension.OVERWORLD, packet.position().toBlockPos()),
				0.0F,
				0.0F
		)));

		connection.send(new S2CGameEventPacket(S2CGameEventPacket.EventType.LEVEL_CHUNKS_LOAD_START, 0));
	}

	public void handleC2SConfigurationAcknowledged(final ClientConnection connection, final C2SConfigurationAcknowledgedPacket packet) {
	}

	public void handleS2CSetTime(final ClientConnection connection, final SetTimePacket packet) {
		connection.send(new S2CSetTimePacket(packet.time(), packet.time(), true));
	}

	public void handleS2CSetHealth(final ClientConnection connection, final SetHealthPacket packet) {
		final float health = Math.max(0, packet.health());
		connection.send(new S2CSetHealthPacket(health));
		if (health == 0) {
			connection.send(new S2CGameEventPacket(S2CGameEventPacket.EventType.IMMEDIATE_RESPAWN, 0));
		}
	}

	// TODO: Move to data-gen
	private ModernEntityType mapBetaToModernEntityId(final BetaEntityType type) {
		// TODO: mobs w/ metadata
		if (type == BetaEntityType.SKELETON) {
			return ModernEntityType.SKELETON;
		} else if (type == BetaEntityType.SPIDER) {
			return ModernEntityType.SPIDER;
		} else if (type == BetaEntityType.GIANT) {
			return ModernEntityType.GIANT;
		} else if (type == BetaEntityType.ZOMBIE) {
			return ModernEntityType.ZOMBIE;
		} else if (type == BetaEntityType.ZOMBIE_PIGMEN) {
			return ModernEntityType.PIGLIN;
		} else if (type == BetaEntityType.COW) {
			return ModernEntityType.COW;
		} else if (type == BetaEntityType.CHICKEN) {
			return ModernEntityType.CHICKEN;
		} else if (type == BetaEntityType.SQUID) {
			return ModernEntityType.SQUID;
		}

		return null; // Unhandled
	}

	public void handleS2CAddMob(final ClientConnection connection, final AddMobPacket packet) {
		final ModernEntityType mappedType = mapBetaToModernEntityId(packet.type());
		if (mappedType == null) {
			return;
		}

		connection.send(new S2CAddEntityPacket(
				packet.entityId(),
				UUID.randomUUID(),
				mappedType,
				packet.getPosition(),
				Vec3d.ZERO,
				packet.yRot(),
				packet.xRot(),
				(byte) 0,
				0
		));
	}

	public void handleS2CAddEntity(final ClientConnection connection, final AddEntityPacket packet) {
		final ModernEntityType mappedType = mapBetaToModernEntityId(packet.type());
		if (mappedType == null) {
			return;
		}

		connection.send(new S2CAddEntityPacket(
				packet.entityId(),
				UUID.randomUUID(),
				mappedType,
				packet.getPosition(),
				new Vec3d(packet.xd(), packet.yd(), packet.zd()),
				(byte) 0,
				(byte) 0,
				(byte) 0,
				0
		));
	}

	private UUID lookupUUID(final int entityId) {
		return UUID.randomUUID();
	}

	public void handleS2CAddPlayer(final ClientConnection connection, final AddPlayerPacket packet) {
		final UUID uuid = lookupUUID(packet.entityId());
		connection.send(S2CPlayerInfoUpdatePacket.addPlayer(new GameProfile(uuid, packet.name())));
		connection.send(new S2CAddEntityPacket(
				packet.entityId(),
				uuid,
				ModernEntityType.PLAYER,
				packet.getPosition(),
				Vec3d.ZERO,
				packet.packedXRot(),
				packet.packedYRot(),
				(byte) 0,
				0
		));
	}

	public void handleS2CAddItemEntity(final ClientConnection connection, final AddItemEntityPacket packet) {
		connection.send(new S2CAddEntityPacket(
				packet.entityId(),
				UUID.randomUUID(),
				ModernEntityType.ITEM,
				packet.getPosition(),
				packet.getMovement(),
				0,
				0,
				(byte) 0,
				0
		));

		connection.send(new S2CSetEntityDataPacket(
				packet.entityId(),
				List.of(new ModernSynchedEntityData.DataValue<>((byte) 8, EntityDataSerializers.ITEM_STACK, ItemTranslator.toModernStack(packet.item())))
		));
	}

	public void handleC2SSwing(final ClientConnection connection, final C2SSwingPacket packet) {
		if (packet.hand() == InteractionHand.MAIN_HAND) {
			connection.getServerConnection().send(new AnimatePacket(this.player.getId(), AnimatePacket.Action.SWING_ARM));
		}
	}

	public void handleS2CAnimate(final ClientConnection connection, final AnimatePacket packet) {
		switch (packet.action()) {
			case AnimatePacket.Action.SWING_ARM ->
					connection.send(new S2CAnimatePacket(packet.entityId(), S2CAnimatePacket.SWING_MAIN_HAND));

			case AnimatePacket.Action.DAMAGE_ANIMATION -> {
				// TODO
			}

			case AnimatePacket.Action.LEAVE_BED ->
					connection.send(new S2CAnimatePacket(packet.entityId(), S2CAnimatePacket.WAKE_UP));
		}
	}

	public void handleS2CGameEvent(final ClientConnection connection, final GameEventPacket packet) {
		if (packet.type() == GameEventPacket.Type.INVALID_BED) {
			connection.send(new S2CSystemChatPacket(TextComponent.translation("block.minecraft.spawn.not_valid"), false));
		} else if (packet.type() == GameEventPacket.Type.BEGIN_RAINING) {
			connection.send(new S2CGameEventPacket(S2CGameEventPacket.EventType.START_RAINING, 0.0F));
		} else if (packet.type() == GameEventPacket.Type.END_RAINING) {
			connection.send(new S2CGameEventPacket(S2CGameEventPacket.EventType.STOP_RAINING, 0.0F));
		}
	}

	public void handleC2SInteract(final ClientConnection connection, final C2SInteractPacket packet) {
		final ServerConnection serverConnection = connection.getServerConnection();
		if (packet.action() instanceof C2SInteractPacket.AttackAction) {
			serverConnection.send(new InteractPacket(this.player.getId(), packet.entityId(), true));
		} else {
			// TODO: InteractionPacket (Use Bed (0x11))
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
		final int xs = packet.xs() + 1;
		final int ys = packet.ys() + 1;
		final int zs = packet.zs() + 1;
		final byte[] buffer = new byte[xs * ys * zs * 5 / 2];
		try (final Inflater inflater = new Inflater()) {
			inflater.setInput(packet.data());
			inflater.inflate(buffer);
		} catch (final DataFormatException exception) {
			connection.kick("Bad compressed data format");
			return;
		}

		ChunkTranslator.readBetaRegionData(connection, packet.x(), packet.y(), packet.z(), xs, ys, zs, buffer);
	}

	public void handleS2CSetCarriedItem(final ClientConnection connection, final SetCarriedItemPacket packet) {
		connection.send(new C2SSetCarriedItemPacket(packet.slot()));
	}

	public void handleC2SSetCarriedItem(final ClientConnection connection, final C2SSetCarriedItemPacket packet) {
		connection.getServerConnection().send(new SetCarriedItemPacket(packet.slot()));
	}

	public void handleC2STickEnd(final ClientConnection connection, final C2SClientTickEndPacket packet) {
		connection.tick();
		this.player.tick();
	}

	public void handleBetaMovePlayer(final ClientConnection connection, final MovePlayerPacket packet) {
		this.player.updateFromServer(packet);
	}

	public void handleC2SMovePlayerPos(final ClientConnection connection, final C2SMovePlayerPacket packet) {
		this.player.updateFromClient(packet);
	}

	public void handleS2CSetEntityMotion(final ClientConnection connection, final SetEntityMotionPacket packet) {
		connection.send(new S2CSetEntityMotionPacket(packet.id(), new Vec3d(packet.deltaX(), packet.deltaY(), packet.deltaZ())));
	}

	public void handleC2SPlayerInput(final ClientConnection connection, final C2SPlayerInputPacket packet) {
		this.player.setSneaking(packet.input().shift());
	}

	public void handleC2SContainerSlotStateChanged(final ClientConnection connection, final C2SContainerSlotStateChangedPacket packet) {
		// TODO?
	}

	public void handleC2SC2SContainerClick(final ClientConnection connection, final C2SContainerClickPacket packet) {
		LOGGER.info("Container Click: (containerId={}, slot={}, button={}, clickType={})", packet.containerId(), packet.slot(), packet.button(), packet.clickType());

		BetaItemStack stack = BetaItemStack.EMPTY;
		if (packet.carriedItem() instanceof HashedStack.ActualItem(int itemId, int count, HashedPatchMap components)) {
			stack = ItemTranslator.toBetaStack(new ModernItemStack(itemId, count, DataComponentPatch.EMPTY)); // TODO: components
		}

		if (packet.slot() == 45) {
			// Cancel Offhand
			// TODO: put item back in cursor/don't drop
			return;
		}

		final short uid = lastUid++;
		connection.getServerConnection().send(new ContainerClickPacket(
				(byte) packet.containerId(),
				packet.slot(),
				packet.button(),
				uid,
				packet.clickType() == C2SContainerClickPacket.ClickType.QUICK_MOVE,
				stack
		));
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
	}

	public void handleS2CContainerSetData(final ClientConnection connection, final ContainerSetDataPacket packet) {
		connection.send(new S2CContainerSetDataPacket(packet.containerId(), packet.id(), packet.value()));
	}

	public void handleS2CContainerAck(final ClientConnection connection, final ContainerAckPacket packet) {
		connection.getServerConnection().send(new ContainerAckPacket(packet.containerId(), packet.uid(), true));
	}

	// TODO: datagen?
	private static S2COpenScreenPacket.WindowType betaToModernMenuType(final ContainerOpenPacket.MenuType type, final int size) {
		return switch (type) {
			case BASIC -> S2COpenScreenPacket.WindowType.genericBySize(size);
			case CRAFTING -> S2COpenScreenPacket.WindowType.CRAFTING; // Crafting Table
			case FURNACE -> S2COpenScreenPacket.WindowType.FURNACE; // Furnace
			case DISPENSER -> S2COpenScreenPacket.WindowType.GENERIC_3x3; // Dispenser/Dropper
		};
	}

	public void handleS2CContainerOpen(final ClientConnection connection, final ContainerOpenPacket packet) {
		final S2COpenScreenPacket.WindowType windowType = betaToModernMenuType(packet.type(), packet.size());
		if (windowType == null) {
			return;
		}

		connection.send(new S2COpenScreenPacket(packet.containerId(), windowType, TextComponent.of(packet.title())));
	}

	public void handleS2CContainerClose(final ClientConnection connection, final ContainerClosePacket packet) {
		connection.send(new S2CContainerClosePacket(packet.containerId()));
	}

	public void handleC2SContainerClose(final ClientConnection connection, final C2SContainerClosePacket packet) {
		connection.getServerConnection().send(new ContainerClosePacket((byte) packet.containerId()));
	}

	public void handleC2SPlayerAction(final ClientConnection connection, final C2SPlayerActionPacket packet) {
		final ServerConnection serverConnection = connection.getServerConnection();
		final BlockPos blockPos = packet.blockPos();
		final Vec3i pos = new Vec3i(blockPos.x(), blockPos.y(), blockPos.z());
		switch (packet.action()) {
			case START_DESTROY_BLOCK ->
					serverConnection.send(new PlayerActionPacket(PlayerActionPacket.Action.STARTED_DIGGING, pos, (byte) packet.direction().ordinal()));

			case ABORT_DESTROY_BLOCK -> {
				// TODO
				LOGGER.info("abort destroy");
			}

			case STOP_DESTROY_BLOCK ->
					serverConnection.send(new PlayerActionPacket(PlayerActionPacket.Action.FINISHED_DIGGING, pos, (byte) packet.direction().ordinal()));

			// TODO: other types
		}
	}

	public void handleS2CDisconnect(final ClientConnection connection, final DisconnectPacket packet) {
		connection.kick(packet.reason());
	}

	public void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	public void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
