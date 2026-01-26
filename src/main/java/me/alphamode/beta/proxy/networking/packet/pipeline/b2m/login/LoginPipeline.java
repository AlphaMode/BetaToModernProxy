package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.login;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusPingRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CRegistryDataPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CUpdateTagsPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusPongResponsePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.play.PlayPipeline;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;
import me.alphamode.beta.proxy.util.data.modern.RegistrySynchronization;
import me.alphamode.beta.proxy.util.data.modern.ServerStatus;
import me.alphamode.beta.proxy.util.data.modern.TagNetworkSerialization;
import me.alphamode.beta.proxy.util.data.modern.registry.Registry;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.IntArrayTag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class LoginPipeline {
	private static final Logger LOGGER = LogManager.getLogger(LoginPipeline.class);
	public static final PacketPipeline<LoginPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<LoginPipeline>builder()
			// Keep Alive
			.clientHandler(C2SCommonKeepAlivePacket.class, LoginPipeline::handleC2SKeepAlive)
			.serverHandler(KeepAlivePacket.class, LoginPipeline::handleS2CKeepAlive)
			// Intent
			.clientHandler(C2SIntentionPacket.class, LoginPipeline::handleClientIntent)
			// Status
			.clientHandler(C2SStatusRequestPacket.class, LoginPipeline::handleC2SStatusRequest)
			.clientHandler(C2SStatusPingRequestPacket.class, LoginPipeline::handleC2SStatusPingRequest)
			// Login
			.clientHandler(C2SHelloPacket.class, LoginPipeline::handleC2SHello)
			.serverHandler(HandshakePacket.class, LoginPipeline::handleS2CHandshake)
			.serverHandler(LoginPacket.class, LoginPipeline::handleS2CLogin)
			// Configuration
			.clientHandler(C2SLoginAcknowledgedPacket.class, LoginPipeline::handleC2SLoginAcknowledged)
			.clientHandler(C2SFinishConfigurationPacket.class, LoginPipeline::handleC2SFinishConfiguration)
			// Unhandled
			.unhandledClient(LoginPipeline::passClientToNextPipeline)
			.unhandledServer(LoginPipeline::passServerToNextPipeline)
			.build();

	// Keep Alive (Handshake, Login, Play)
	private void handleC2SKeepAlive(final ClientConnection connection, final C2SCommonKeepAlivePacket<?> packet) {
		connection.getServerConnection().send(new KeepAlivePacket());
		LOGGER.info("Sending keep alive to server");
	}

	private void handleS2CKeepAlive(final ClientConnection connection, final KeepAlivePacket packet) {
		final long lastKeepAliveMs = connection.getLastKeepAliveMS();
		connection.setLastKeepAliveMS(System.currentTimeMillis());

		final S2CCommonKeepAlivePacket<?> keepAlivePacket = connection.createKeepAlivePacket(System.currentTimeMillis() - lastKeepAliveMs);
		if (keepAlivePacket != null) {
			LOGGER.info("Sending keep alive to client");
			connection.send(keepAlivePacket);
		}
	}

	// Handshake
	private void handleClientIntent(final ClientConnection connection, final C2SIntentionPacket packet) {
		switch (packet.intention()) {
			case LOGIN -> handleLogin(connection, packet);
			case STATUS -> connection.setState(PacketState.STATUS);
			case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
		}

		connection.setProtocolVersion(packet.protocolVersion());
	}

	// Status
	private void handleC2SStatusRequest(final ClientConnection connection, final C2SStatusRequestPacket packet) {
		final BrodernProxy proxy = BrodernProxy.getProxy();
		final ServerStatus serverStatus = new ServerStatus(
				proxy.config().getMessage().append(String.format("\n(Connected To Server? %s)", connection.getServerConnection().isConnected())),
				Optional.of(new ServerStatus.Players(proxy.config().getMaxPlayers(), 0, List.of())),
				Optional.of(new ServerStatus.Version(proxy.config().getBrand(), ModernPacket.PROTOCOL_VERSION)),
				Optional.empty(),
				false
		);
		connection.send(new S2CStatusResponsePacket(serverStatus));
	}

	private void handleC2SStatusPingRequest(final ClientConnection connection, final C2SStatusPingRequestPacket packet) {
		connection.send(new S2CStatusPongResponsePacket(packet.time()));
		connection.disconnect();
	}

	// Login
	private void handleLogin(final ClientConnection connection, final C2SIntentionPacket packet) {
		connection.setState(PacketState.LOGIN);
		if (packet.protocolVersion() != ModernPacket.PROTOCOL_VERSION) {
			connection.kick("Client is on " + packet.protocolVersion() + " while server is on " + ModernPacket.PROTOCOL_VERSION);
		} else if (!connection.getServerConnection().isConnected()) {
			connection.kick("Server is not connected!");
		}
	}

	private void handleC2SHello(final ClientConnection connection, final C2SHelloPacket packet) {
		LOGGER.info("Sending Handshake Packet");
		final GameProfile profile = new GameProfile(packet.profileId(), packet.username(), new HashMap<>());
		connection.setProfile(profile);
		connection.getServerConnection().send(new HandshakePacket(packet.username()));
	}

	private void handleS2CHandshake(final ClientConnection connection, final HandshakePacket packet) {
		if (packet.username().equals("-")) {
			LOGGER.info("Sending Login Packet & Login Finished");
			connection.getServerConnection().send(new LoginPacket(BetaPacket.PROTOCOL_VERSION, connection.getProfile().name()));
			connection.send(new S2CLoginFinishedPacket(connection.getProfile()));
		} else {
			connection.kick("Online mode isn't supported!");
		}
	}

	// Configuration
	private void handleC2SLoginAcknowledged(final ClientConnection connection, final C2SLoginAcknowledgedPacket packet) {
		LOGGER.info("Starting Configuration");
		connection.setState(PacketState.CONFIGURATION);

		// Send Tags
		this.sendTags(connection);

		// Send Registries
		this.sendRegistries(connection);

		connection.send(S2CFinishConfigurationPacket.INSTANCE);
	}

	private void sendTags(final ClientConnection connection) {
		LOGGER.info("Sending Tags");

		final Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> tags = new HashMap<>();
		BrodernProxy.getDefaultTags().forEach(entry -> {
			final Map<Identifier, IntList> map = new HashMap<>();

			entry.getValue().asCompoundTag().forEach(tag -> {
				final IntList list = new IntArrayList();
				final IntArrayTag listTag = tag.getValue().asIntArrayTag();
				listTag.forEach(list::add);
				map.put(Identifier.of(tag.getKey()), list);
			});

			final TagNetworkSerialization.NetworkPayload payload = new TagNetworkSerialization.NetworkPayload(map);
			tags.put(ResourceKey.createRegistryKey(Identifier.of(entry.getKey())), payload);
		});

		connection.send(new S2CUpdateTagsPacket(tags));
	}

	private void sendRegistries(final ClientConnection connection) {
		LOGGER.info("Sending Registries");

		BrodernProxy.getDefaultRegistries().forEach(entry -> {
			final List<RegistrySynchronization.PackedRegistryEntry> entries = new ArrayList<>();

			final CompoundTag tag = entry.getValue().asCompoundTag();
			tag.forEach(registryEntry -> {
				final NbtTag value = registryEntry.getValue();
				entries.add(new RegistrySynchronization.PackedRegistryEntry(
						Identifier.of(registryEntry.getKey()),
						Optional.ofNullable(value.asCompoundTag())
				));
			});

			connection.send(new S2CRegistryDataPacket(
					ResourceKey.createRegistryKey(Identifier.of(entry.getKey())),
					entries
			));
		});
	}

	private void handleC2SFinishConfiguration(final ClientConnection connection, final C2SFinishConfigurationPacket packet) {
		LOGGER.info("Finished Configuration & Going into Play Mode");
		connection.setState(PacketState.PLAY);
		connection.setPipeline(PlayPipeline.PIPELINE, new PlayPipeline()); // TODO: Pass in unhandled packets
	}

	private void handleS2CLogin(final ClientConnection connection, final LoginPacket packet) {
// 		connection.send(new S2CPlayLoginPacket(
//				0, // TODO
//				false,
//				List.of(Dimension.OVERWORLD, Dimension.NETHER, Dimension.SKY),
//				BrodernProxy.getProxy().config().getMaxPlayers(),
//				16,
//				16,
//				false,
//				false,
//				false,
//				new CommonPlayerSpawnInfo(
//						null, // TODO (Holder<DimensionType>)
//						Dimension.byLegacyId(packet.dimension()),
//						packet.seed(),
//						GameMode.SURVIVAL,
//						GameMode.SURVIVAL,
//						false,
//						false,
//						Optional.empty(),
//						300,
//						63
//				),
//				false
//		));
	}

	private void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	private void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
