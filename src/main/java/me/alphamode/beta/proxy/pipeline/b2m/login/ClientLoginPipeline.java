package me.alphamode.beta.proxy.pipeline.b2m.login;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SConfigurationKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusPingRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CConfigurationCustomPayloadPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CRegistryDataPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CUpdateTagsPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusPongResponsePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.pipeline.PacketPipeline;
import me.alphamode.beta.proxy.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
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

public class ClientLoginPipeline {
	private static final Logger LOGGER = LogManager.getLogger(ClientLoginPipeline.class);
	public static final PacketPipeline<ClientLoginPipeline, BetaPacket, ModernPacket<?>> PIPELINE = BetaToModernPipeline.<ClientLoginPipeline>builder()
			// Intent
			.clientHandler(C2SIntentionPacket.class, ClientLoginPipeline::handleClientIntent)
			// Status
			.clientHandler(C2SStatusRequestPacket.class, ClientLoginPipeline::handleC2SStatusRequest)
			.clientHandler(C2SStatusPingRequestPacket.class, ClientLoginPipeline::handleC2SStatusPingRequest)
			// Login
			.clientHandler(C2SHelloPacket.class, ClientLoginPipeline::handleC2SHello)
			// Configuration
			.clientHandler(C2SConfigurationKeepAlivePacket.class, ClientLoginPipeline::handleC2SKeepAlive)
			.serverHandler(KeepAlivePacket.class, ClientLoginPipeline::handleS2CKeepAlive)
			.clientHandler(C2SLoginAcknowledgedPacket.class, ClientLoginPipeline::handleC2SLoginAcknowledged)
			.clientHandler(C2SFinishConfigurationPacket.class, ClientLoginPipeline::handleC2SFinishConfiguration)
			.serverHandler(DisconnectPacket.class, ClientLoginPipeline::handleS2CDisconnect)
			// there is no C2SDisconnect packet?
			// Unhandled
			.unhandledClient(ClientLoginPipeline::passClientToNextPipeline)
			.unhandledServer(ClientLoginPipeline::passServerToNextPipeline)
			.build();

	// Handshake
	public void handleClientIntent(final ClientConnection connection, final C2SIntentionPacket packet) {
		switch (packet.intention()) {
			case LOGIN -> handleLogin(connection, packet);
			case STATUS -> connection.setState(PacketState.STATUS);
			case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
		}

		connection.setProtocolVersion(packet.protocolVersion());
	}

	// Status
	public void handleC2SStatusRequest(final ClientConnection connection, final C2SStatusRequestPacket packet) {
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

	public void handleC2SStatusPingRequest(final ClientConnection connection, final C2SStatusPingRequestPacket packet) {
		connection.send(new S2CStatusPongResponsePacket(packet.time()));
		connection.disconnect();
	}

	// Login
	public void handleLogin(final ClientConnection connection, final C2SIntentionPacket packet) {
		connection.setState(PacketState.LOGIN);
		if (packet.protocolVersion() != ModernPacket.PROTOCOL_VERSION) {
			connection.kick("Client is on " + packet.protocolVersion() + " while server is on " + ModernPacket.PROTOCOL_VERSION);
		} else if (!connection.getServerConnection().isConnected()) {
			connection.kick("Server is not connected!");
		}
	}

	public void handleC2SHello(final ClientConnection connection, final C2SHelloPacket packet) {
		LOGGER.info("Sending Handshake Packet");
		final GameProfile profile = new GameProfile(packet.profileId(), packet.username(), new HashMap<>());
		connection.setProfile(profile);
        connection.send(new S2CLoginFinishedPacket(profile));
	}

	// Configuration
	public void handleC2SKeepAlive(final ClientConnection connection, final C2SConfigurationKeepAlivePacket packet) {
		LOGGER.error("(Configuration) Sending keep alive to server");
		connection.getServerConnection().send(new KeepAlivePacket());
		connection.setLastKeepAliveId(packet.id());
	}

	public void handleS2CKeepAlive(final ClientConnection connection, final KeepAlivePacket packet) {
		LOGGER.error("Sending keep alive to client");
		final S2CCommonKeepAlivePacket<?> keepAlivePacket = connection.createKeepAlivePacket(connection.getLastKeepAliveId());
		if (keepAlivePacket != null) {
			connection.send(keepAlivePacket);
		}
	}

	public void handleS2CDisconnect(final ClientConnection connection, final DisconnectPacket packet) {
		connection.kick(packet.reason());
	}

	public void handleC2SLoginAcknowledged(final ClientConnection connection, final C2SLoginAcknowledgedPacket packet) {
		LOGGER.info("Starting Configuration");
		connection.setState(PacketState.CONFIGURATION);

        // Send Tags
        this.sendTags(connection);

        // Send Registries
        this.sendRegistries(connection);

        // Send Custom Brand
        final ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        ModernStreamCodecs.stringUtf8().encode(buf, BrodernProxy.getProxy().config().getBrand());

        final byte[] buffer = new byte[buf.readableBytes()];
        buf.readBytes(buffer);
        connection.send(new S2CConfigurationCustomPayloadPacket(Identifier.defaultNamespace("brand"), buffer));
        buf.release();

        connection.send(S2CFinishConfigurationPacket.INSTANCE);
	}

	public void sendTags(final ClientConnection connection) {
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

	public void sendRegistries(final ClientConnection connection) {
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

	public void handleC2SFinishConfiguration(final ClientConnection connection, final C2SFinishConfigurationPacket packet) {
		LOGGER.info("Finished Configuration & Going into Play Mode");
		connection.setState(PacketState.PLAY);
        // Done with client login now do server login
		connection.setPipeline(ServerLoginPipeline.PIPELINE, new ServerLoginPipeline()); // TODO: Pass in unhandled packets
        connection.getServerConnection().send(new HandshakePacket(connection.getProfile().name()));
    }

	public void passClientToNextPipeline(final ClientConnection connection, final ModernPacket<?> packet) {
	}

	public void passServerToNextPipeline(final ClientConnection connection, final BetaPacket packet) {
	}
}
