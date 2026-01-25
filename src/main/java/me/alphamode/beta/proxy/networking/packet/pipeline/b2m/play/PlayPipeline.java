package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play;

import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.ChatPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PlayPipeline {
	private static final Logger LOGGER = LogManager.getLogger(PlayPipeline.class);
	public static final PacketPipeline<PlayPipeline, BetaRecordPacket, ModernRecordPacket<?>> PIPELINE = BetaToModernPipeline.<PlayPipeline>builder()
			.clientHandler(C2SConfigurationAcknowledgedPacket.class, PlayPipeline::handleC2SConfigurationAcknowledged)
			.serverHandler(ChatPacket.class, PlayPipeline::handleS2CChat)
			.unhandledClient(PlayPipeline::passClientToNextPipeline)
			.unhandledServer(PlayPipeline::passServerToNextPipeline)
			.build();

	public void handleC2SConfigurationAcknowledged(final ClientConnection connection, final C2SConfigurationAcknowledgedPacket packet) {

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

	private void passClientToNextPipeline(final ClientConnection connection, final ModernRecordPacket<?> packet) {
	}

	private void passServerToNextPipeline(final ClientConnection connection, final BetaRecordPacket packet) {
	}
}
