package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.ChatPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.SetSpawnPositionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SChatPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CPlayLoginPacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.util.data.modern.CommonPlayerSpawnInfo;
import me.alphamode.beta.proxy.util.data.modern.enums.GameMode;
import me.alphamode.beta.proxy.util.data.modern.registry.Registries;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class PlayPipeline {
	private static final Logger LOGGER = LogManager.getLogger(PlayPipeline.class);
	public static final PacketPipeline<PlayPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<PlayPipeline>builder()
			.clientHandler(C2SConfigurationAcknowledgedPacket.class, PlayPipeline::handleC2SConfigurationAcknowledged)
			.clientHandler(C2SChatPacket.class, PlayPipeline::handleC2SChat)
			.serverHandler(ChatPacket.class, PlayPipeline::handleS2CChat)
			.serverHandler(SetSpawnPositionPacket.class, PlayPipeline::handleS2CSetSpawnPosition)
			.unhandledClient(PlayPipeline::passClientToNextPipeline)
			.unhandledServer(PlayPipeline::passServerToNextPipeline)
			.build();

	private final long seed;

	public PlayPipeline(final long seed) {
		this.seed = seed;
	}

	public void handleS2CSetSpawnPosition(final ClientConnection connection, final SetSpawnPositionPacket packet) {
		final CommonPlayerSpawnInfo spawnInfo = new CommonPlayerSpawnInfo(
				null, // TODO
				ResourceKey.create(Registries.DIMENSION, Identifier.defaultNamespace("overworld")),
				this.seed,
				GameMode.SURVIVAL, // TODO
				GameMode.SURVIVAL, // TODO
				false,
				false,
				Optional.empty(),
				20,
				63
		);

		connection.send(new S2CPlayLoginPacket(
				0,
				false,
				List.of(),
				10,
				10,
				10,
				false,
				false,
				false,
				spawnInfo,
				false
		));
	}

	public void handleC2SConfigurationAcknowledged(final ClientConnection connection, final C2SConfigurationAcknowledgedPacket packet) {
	}

	public void handleC2SChat(final ClientConnection connection, final C2SChatPacket packet) {
		connection.getServerConnection().send(new ChatPacket(packet.message()));
	}

	private void handleS2CChat(final ClientConnection connection, final ChatPacket packet) {
		final String message = packet.message();
		LOGGER.info("{}", message);

		final String repeatCommand = "p!repeat";
		if (BrodernProxy.getProxy().isDebug() && message.contains(repeatCommand)) {
			final int index = message.indexOf(repeatCommand);
			connection.getServerConnection().send(new ChatPacket(message.substring(index + repeatCommand.length()).trim()));
		}
	}

	private void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	private void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
