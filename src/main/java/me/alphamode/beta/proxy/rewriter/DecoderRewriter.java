package me.alphamode.beta.proxy.rewriter;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SClientInformationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SConfigurationCustomPayloadPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SCustomQueryAnswerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SConfigurationAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusPingRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CRegistryDataPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CUpdateTagsPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CLevelChunkPacketData;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusPongResponsePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
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

public final class DecoderRewriter extends Rewriter<ModernRecordPacket<?>> {
	private static final Logger LOGGER = LogManager.getLogger(DecoderRewriter.class);

	@Override
	public void registerPackets() {
		this.registerServerboundRewriter(C2SIntentionPacket.class, (connection, packet) -> {
			switch (packet.intention()) {
				case LOGIN -> connection.setState(PacketState.LOGIN);
				case STATUS -> connection.setState(PacketState.STATUS);
				case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
			}

			connection.setProtocolVersion(packet.protocolVersion());
		});

		this.registerServerboundRewriter(C2SHelloPacket.class, (connection, packet) -> {
            connection.sendToServer(new HandshakePacket(packet.username()));
			connection.setUsername(packet.username());
			connection.setId(packet.profileId());
			connection.sendToClient(new S2CLoginFinishedPacket(new GameProfile(packet.profileId(), packet.username(), new HashMap<>())));
		});

		this.registerServerboundRewriter(C2SStatusRequestPacket.class, (connection, _) -> {
			final BrodernProxy proxy = BrodernProxy.getProxy();
			final ServerStatus serverStatus = new ServerStatus(
					proxy.config().getMessage().append(String.format("\n(Connected To Server? %s)", connection.isConnectedToServer())),
					Optional.of(new ServerStatus.Players(proxy.config().getMaxPlayers(), 0, List.of())),
					Optional.of(new ServerStatus.Version(proxy.config().getBrand(), ModernRecordPacket.PROTOCOL_VERSION)),
					Optional.empty(),
					false
			);
			connection.sendToClient(new S2CStatusResponsePacket(serverStatus));
		});

		this.registerServerboundRewriter(C2SStatusPingRequestPacket.class, (connection, _) -> {
			connection.sendToClient(new S2CStatusPongResponsePacket(0L));
		});

		this.registerServerboundRewriter(C2SKeepAlivePacket.class, (connection, _) -> connection.sendToServer(new KeepAlivePacket()));

		this.registerServerboundRewriter(C2SLoginAcknowledgedPacket.class, (connection, _) -> {
			connection.setState(PacketState.CONFIGURATION);

			// Send Tags
			this.sendTags(connection);

			// Send Registries
			this.sendRegistries(connection);

			connection.sendToClient(S2CFinishConfigurationPacket.INSTANCE);
		});

		this.registerServerboundRewriter(C2SFinishConfigurationPacket.class, (connection, _) -> {
			connection.setState(PacketState.PLAY);
			connection.sendToClient(new S2CLevelChunkPacketData());
		});

		this.registerServerboundRewriter(C2SConfigurationAcknowledgedPacket.class, (connection, packet) -> {
			LOGGER.info("meow meow");
		});

		// Cancel
		this.registerServerboundRewriter(C2SConfigurationCustomPayloadPacket.class, (_, _) -> {
		});
		this.registerServerboundRewriter(C2SCustomQueryAnswerPacket.class, (_, _) -> {
		});
		this.registerServerboundRewriter(C2SClientInformationPacket.class, (_, _) -> {
		});
	}

	private void sendTags(final Connection connection) {
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

		connection.sendToClient(new S2CUpdateTagsPacket(tags));
	}

	private void sendRegistries(final Connection connection) {
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

			connection.sendToClient(new S2CRegistryDataPacket(
					ResourceKey.createRegistryKey(Identifier.of(entry.getKey())),
					entries
			));
		});
	}

	@Override
	public void rewrite(final Connection connection, final ModernRecordPacket<?> packet) {
//		LOGGER.warn("Decoding Modern to Beta Packet ({})", packet.getType());
		final RewriterFactory<ModernRecordPacket<?>> rewriter = this.getServerboundRewriter(packet.getClass());
		if (rewriter != null) {
			rewriter.rewrite(connection, packet);
		}
	}
}
