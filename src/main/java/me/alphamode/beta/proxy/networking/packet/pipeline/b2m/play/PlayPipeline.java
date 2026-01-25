package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.*;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SChatPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonDisconnectPacket;
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
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class PlayPipeline {
	private static final Logger LOGGER = LogManager.getLogger(PlayPipeline.class);
	public static final PacketPipeline<PlayPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<PlayPipeline>builder()
			.clientHandler(C2SConfigurationAcknowledgedPacket.class, PlayPipeline::handleC2SConfigurationAcknowledged)
			.serverHandler(SetSpawnPositionPacket.class, PlayPipeline::handleS2CSetSpawnPosition)
			.serverHandler(ChatPacket.class, PlayPipeline::handleS2CChat)
			.clientHandler(C2SChatPacket.class, PlayPipeline::handleC2SChat)
			.serverHandler(DisconnectPacket.class, PlayPipeline::handleS2CDisconnect)
			.clientHandler(S2CCommonDisconnectPacket.class, PlayPipeline::handleC2SDisconnect)
            .serverHandler(MovePlayerPacket.class, PlayPipeline::handleMovePlayer)
            .serverHandler(BlockRegionUpdatePacket.class, PlayPipeline::handleBlockRegionUpdate)
			.unhandledClient(PlayPipeline::passClientToNextPipeline)
			.unhandledServer(PlayPipeline::passServerToNextPipeline)
			.build();

	/**
	 * Dear AlphaMode
	 * This code is just for fun/to try and get into the world, it is most likely not accurate to what you are wanting to do
	 * Sincerely, lowercase of the btw
	 */
	private void handleS2CSetSpawnPosition(final ClientConnection connection, final SetSpawnPositionPacket packet) {
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

	private void handleC2SConfigurationAcknowledged(final ClientConnection connection, final C2SConfigurationAcknowledgedPacket packet) {
	}

    public void handleMovePlayer(final ClientConnection connection, final MovePlayerPacket packet) {
        connection.getServerConnection().send(packet);
    }

    public void handleBlockRegionUpdate(final ClientConnection connection, final BlockRegionUpdatePacket packet) {
        byte[] buffer = new byte[packet.xs() * packet.ys() * packet.zs() * 5 / 2];
        try (Inflater inflater = new Inflater()) {
            inflater.setInput(packet.data());
            inflater.inflate(buffer);
        } catch (DataFormatException e) {
            connection.kick("Bad compressed data format");
        }
        if (BrodernProxy.getProxy().isDebug()) {
            LOGGER.info("Decompressed beta block region data: {}", buffer);
        }
    }

	private void handleS2CChat(final ClientConnection connection, final ChatPacket packet) {
		final String message = packet.message();
		LOGGER.info("{}", message);
		connection.send(new S2CSystemChatPacket(TextComponent.of(message), false));
	}

	private void handleC2SChat(final ClientConnection connection, final C2SChatPacket packet) {
		connection.getServerConnection().send(new ChatPacket(packet.message()));
	}

	private void handleS2CDisconnect(final ClientConnection connection, final DisconnectPacket packet) {
		connection.kick(packet.reason());
	}

	private void handleC2SDisconnect(final ClientConnection connection, final S2CCommonDisconnectPacket<?> packet) {
		connection.getServerConnection().send(new DisconnectPacket(packet.getReason().asLegacyFormatString()));
	}

	private void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	private void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
