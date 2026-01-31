package me.alphamode.beta.proxy.pipeline.b2m.login;

import me.alphamode.beta.proxy.entity.Player;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.pipeline.b2m.play.PlayPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerLoginPipeline {
	private static final Logger LOGGER = LogManager.getLogger(ServerLoginPipeline.class);
	public static final PacketPipeline<ServerLoginPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<ServerLoginPipeline>builder()
			// Handshake
			.serverHandler(HandshakePacket.class, ServerLoginPipeline::handleS2CHandshake)
			.serverHandler(LoginPacket.class, ServerLoginPipeline::handleS2CLogin)
			.serverHandler(DisconnectPacket.class, ServerLoginPipeline::handleS2CDisconnect)
			// there is no C2SDisconnect packet?
			// Unhandled
			.unhandledClient(ServerLoginPipeline::passClientToNextPipeline)
			.unhandledServer(ServerLoginPipeline::passServerToNextPipeline)
			.build();

	public void handleS2CHandshake(final ClientConnection connection, final HandshakePacket packet) {
		if (packet.username().equals("-")) {
			LOGGER.info("Sending Login Packet & Login Finished");
			connection.getServerConnection().send(new LoginPacket(BetaPacket.PROTOCOL_VERSION, connection.getProfile().name()));
		} else {
			connection.kick("Online mode isn't supported!");
		}
	}

	public void handleS2CLogin(final ClientConnection connection, final LoginPacket packet) {
		final Player player = new Player(packet.clientVersion(), connection);
		player.setDimension(packet.dimension());
		connection.setPipeline(PlayPipeline.PIPELINE, new PlayPipeline(player));
	}

	public void handleS2CDisconnect(final ClientConnection connection, final DisconnectPacket packet) {
		connection.kick(packet.reason());
	}

	public void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	public void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
