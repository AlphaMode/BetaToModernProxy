package me.alphamode.beta.proxy.rewriter;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.Proxy;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketDirection;
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
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CRegistryDataPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CUpdateTagsPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CPongResponsePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;
import me.alphamode.beta.proxy.util.data.modern.RegistrySynchronization;
import me.alphamode.beta.proxy.util.data.modern.TagNetworkSerialization;
import me.alphamode.beta.proxy.util.data.modern.registry.Registry;
import me.alphamode.beta.proxy.util.data.modern.ServerStatus;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;
import net.lenni0451.mcstructs.core.Identifier;
import net.lenni0451.mcstructs.nbt.NbtTag;
import net.lenni0451.mcstructs.nbt.tags.CompoundTag;
import net.lenni0451.mcstructs.nbt.tags.IntArrayTag;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.*;

public final class DecoderRewriter extends Rewriter {
	private final String realServerIp;
	private final CompoundTag defaultTags;
	private final CompoundTag defaultRegistries;

	public DecoderRewriter(final String realServerIp, final CompoundTag defaultTags, final CompoundTag defaultRegistries) {
		// TODO: find better way to handle data like this
		this.realServerIp = realServerIp;
		this.defaultTags = defaultTags;
		this.defaultRegistries = defaultRegistries;
	}

	@Override
	public void registerPackets() {
		this.registerRewriter(C2SIntentionRecordPacket.class, PacketDirection.SERVERBOUND, (connection, packet) -> {
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

		this.registerRewriter(C2SHelloPacket.class, PacketDirection.SERVERBOUND, (connection, packet) -> {
			connection.setUsername(packet.username());
			connection.setId(packet.profileId());
			connection.send(new S2CLoginFinishedPacket(new GameProfile(packet.profileId(), packet.username(), new HashMap<>())));
			return new LoginPacket(packet.username(), BetaRecordPacket.PROTOCOL_VERSION);
		});

		this.registerRewriter(C2SStatusRequestPacket.class, PacketDirection.SERVERBOUND, (connection, _) -> {
            final Proxy proxy = Proxy.getProxy();
            final ServerStatus serverStatus = new ServerStatus(proxy.getMotd(), Optional.of(new ServerStatus.Players(proxy.getConfig().getMaxPlayers(), 0, List.of())), Optional.of(new ServerStatus.Version(proxy.getBrand(), 774)), Optional.empty(), false);
            connection.send(new S2CStatusResponsePacket(serverStatus));
			connection.send(new S2CPongResponsePacket(0L));
			connection.disconnect();
			return null;
		});

		this.registerRewriter(C2SCommonKeepAlivePacket.class, PacketDirection.SERVERBOUND, (_, _) -> new KeepAlivePacket());

		this.registerRewriter(C2SLoginAcknowledgedPacket.class, PacketDirection.SERVERBOUND, (connection, _) -> {
			connection.setState(PacketState.CONFIGURATION);

			// Send Tags
			this.sendTags(connection);

			// Send Registries
			this.sendRegistries(connection);

			connection.send(S2CFinishConfigurationPacket.INSTANCE);
			return null;
		});

		this.registerRewriter(C2SFinishConfigurationPacket.class, PacketDirection.SERVERBOUND, (connection, _) -> {
			connection.setState(PacketState.PLAY);
			//connection.kick("meow meow mrrp :3 nyaaa uwu");
			return null;
		});

		this.registerRewriter(C2SConfigurationAcknowledgedPacket.class, PacketDirection.SERVERBOUND, (connection, packet) -> {
			IO.println("meow meow");
			return null;
		});

		// Cancel
		this.registerRewriter(C2SCommonCustomPayloadPacket.class, PacketDirection.SERVERBOUND, (_, _) -> null);
		this.registerRewriter(C2SCustomQueryAnswerPacket.class, PacketDirection.SERVERBOUND, (_, _) -> null);
		this.registerRewriter(C2SClientInformationPacket.class, PacketDirection.SERVERBOUND, (_, _) -> null);
	}

	private void sendTags(final Connection connection) {
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
