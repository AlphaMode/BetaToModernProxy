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
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonCustomPayloadPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SClientInformationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
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

public final class DecoderRewriter extends Rewriter {
	private static final Logger LOGGER = LogManager.getLogger(DecoderRewriter.class);

	private final CompoundTag defaultTags;
	private final CompoundTag defaultRegistries;

	public DecoderRewriter(final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		// TODO: find better way to handle data like this
		this.defaultTags = defaultTags;
		this.defaultRegistries = defaultRegistries;
	}

	@Override
	public void registerPackets() {
		this.registerServerboundRewriter(C2SIntentionRecordPacket.class, (connection, packet) -> {
			switch (packet.intention()) {
				case LOGIN -> connection.setState(PacketState.LOGIN);
				case STATUS -> connection.setState(PacketState.STATUS);
				case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
			}

			connection.setProtocolVersion(packet.protocolVersion());
			if (packet.intention() == C2SIntentionRecordPacket.ClientIntent.LOGIN) {
				return new HandshakePacket("-");
			} else {
				return null;
			}
		});

		this.registerServerboundRewriter(C2SHelloPacket.class, (connection, packet) -> {
			connection.setUsername(packet.username());
			connection.setId(packet.profileId());
			connection.send(new S2CLoginFinishedPacket(new GameProfile(packet.profileId(), packet.username(), new HashMap<>())));
			return new LoginPacket(BetaRecordPacket.PROTOCOL_VERSION, packet.username(), 0L, (byte) 0);
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
			connection.send(new S2CStatusResponsePacket(serverStatus));
			return null;
		});

		this.registerServerboundRewriter(C2SStatusPingRequestPacket.class, (connection, _) -> {
			connection.send(new S2CStatusPongResponsePacket(0L));
			return null;
		});

		this.registerServerboundRewriter(C2SCommonKeepAlivePacket.class, (_, _) -> new KeepAlivePacket());

		this.registerServerboundRewriter(C2SLoginAcknowledgedPacket.class, (connection, _) -> {
			connection.setState(PacketState.CONFIGURATION);

			// Send Tags
			this.sendTags(connection);

			// Send Registries
			this.sendRegistries(connection);

			connection.send(S2CFinishConfigurationPacket.INSTANCE);
			return null;
		});

		this.registerServerboundRewriter(C2SFinishConfigurationPacket.class, (connection, _) -> {
			connection.setState(PacketState.PLAY);
			return null;
		});

		this.registerServerboundRewriter(C2SConfigurationAcknowledgedPacket.class, (connection, packet) -> {
			LOGGER.info("meow meow");
			return null;
		});

		// Cancel
		this.registerServerboundRewriter(C2SCommonCustomPayloadPacket.class, (_, _) -> null);
		this.registerServerboundRewriter(C2SCustomQueryAnswerPacket.class, (_, _) -> null);
		this.registerServerboundRewriter(C2SClientInformationPacket.class, (_, _) -> null);
	}

	private void sendTags(final Connection connection) {
		LOGGER.info("Sending Tags");

		final Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> tags = new HashMap<>();
		this.defaultTags.forEach(entry -> {
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

	private void sendRegistries(final Connection connection) {
		LOGGER.info("Sending Registries");

		this.defaultRegistries.forEach(entry -> {
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
}
