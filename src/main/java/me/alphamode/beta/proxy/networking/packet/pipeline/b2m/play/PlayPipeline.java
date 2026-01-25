package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.ServerConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.ChatPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.MovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.SetSpawnPositionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SChatPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SMovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.*;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.util.data.modern.CommonPlayerSpawnInfo;
import me.alphamode.beta.proxy.util.data.modern.GlobalPos;
import me.alphamode.beta.proxy.util.data.modern.LevelData;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLevelChunkPacketData;
import me.alphamode.beta.proxy.util.data.modern.level.ClientboundLightUpdatePacketData;
import me.alphamode.beta.proxy.util.data.modern.registry.dimension.Dimension;
import net.lenni0451.mcstructs.text.TextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayPipeline {
	private static final Logger LOGGER = LogManager.getLogger(PlayPipeline.class);
	public static final PacketPipeline<PlayPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<PlayPipeline>builder()
			.serverHandler(SetSpawnPositionPacket.class, PlayPipeline::handleS2CSetSpawnPosition)
			.clientHandler(C2SConfigurationAcknowledgedPacket.class, PlayPipeline::handleC2SConfigurationAcknowledged)
			.serverHandler(ChatPacket.class, PlayPipeline::handleS2CChat)
			.clientHandler(C2SChatPacket.class, PlayPipeline::handleC2SChat)
			.serverHandler(MovePlayerPacket.class, PlayPipeline::handleS2CMovePlayer)
			.clientHandler(C2SMovePlayerPacket.PosRot.class, PlayPipeline::handleC2CMovePlayerPos)
			.clientHandler(C2SMovePlayerPacket.Rot.class, PlayPipeline::handleC2CMovePlayerPos)
			.clientHandler(C2SMovePlayerPacket.Pos.class, PlayPipeline::handleC2CMovePlayerPos)
			.clientHandler(C2SMovePlayerPacket.StatusOnly.class, PlayPipeline::handleC2CMovePlayerPos)
			.serverHandler(DisconnectPacket.class, PlayPipeline::handleS2CDisconnect)
			// there is no C2SDisconnect packet?
			.unhandledClient(PlayPipeline::passClientToNextPipeline)
			.unhandledServer(PlayPipeline::passServerToNextPipeline)
			.build();

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

	public void handleS2CChat(final ClientConnection connection, final ChatPacket packet) {
		final String message = packet.message();
		LOGGER.info("{}", message);
		connection.send(new S2CSystemChatPacket(TextComponent.of(message), false));
	}

	public void handleC2SChat(final ClientConnection connection, final C2SChatPacket packet) {
		connection.getServerConnection().send(new ChatPacket(packet.message()));
	}

	// TODO: double check accuracy
	public void handleS2CMovePlayer(final ClientConnection connection, final MovePlayerPacket packet) {
		if (packet instanceof MovePlayerPacket.PosRot posRot) {
			connection.send(new C2SMovePlayerPacket.PosRot(posRot.x, posRot.y, posRot.z, posRot.yRot, posRot.xRot, posRot.onGround, false));
		} else if (packet instanceof MovePlayerPacket.Rot rot) {
			connection.send(new C2SMovePlayerPacket.Rot(rot.yRot, rot.xRot, rot.onGround, false));
		} else if (packet instanceof MovePlayerPacket.Pos pos) {
			connection.send(new C2SMovePlayerPacket.Pos(pos.x, pos.y, pos.z, pos.yRot, pos.xRot, pos.onGround, false));
		}
	}

	// TODO: double check accuracy
	public void handleC2CMovePlayerPos(final ClientConnection connection, final C2SMovePlayerPacket packet) {
		final ServerConnection serverConnection = connection.getServerConnection();
		if (packet instanceof C2SMovePlayerPacket.PosRot(
				double x, double y, double z, float yRot, float xRot, boolean onGround, boolean horizontalCollision
		)) {
			serverConnection.send(new MovePlayerPacket.PosRot(x, y, y, z, yRot, xRot, onGround));
		} else if (packet instanceof C2SMovePlayerPacket.Rot(
				float yRot, float xRot, boolean onGround, boolean horizontalCollision
		)) {
			serverConnection.send(new MovePlayerPacket.Rot(yRot, xRot, onGround));
		} else if (packet instanceof C2SMovePlayerPacket.Pos(
				double x, double y, double z, float yRot, float xRot, boolean onGround, boolean horizontalCollision
		)) {
			serverConnection.send(new MovePlayerPacket.PosRot(x, y, y, z, yRot, xRot, onGround));
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
