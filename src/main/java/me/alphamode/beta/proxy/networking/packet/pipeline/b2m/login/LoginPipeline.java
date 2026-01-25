package me.alphamode.beta.proxy.networking.packet.pipeline.b2m.login;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.packet.beta.packets.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.bidirectional.SetSpawnPositionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusPingRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CRegistryDataPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CUpdateTagsPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusPongResponsePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.networking.packet.pipeline.b2m.BetaToModernPipeline;
import me.alphamode.beta.proxy.rewriter.DecoderRewriter;
import me.alphamode.beta.proxy.util.data.Vec3i;
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

public class LoginPipeline extends BetaToModernPipeline {
    private static final Logger LOGGER = LogManager.getLogger(LoginPipeline.class);

    @Override
    public void buildPipeline(final Builder<BetaRecordPacket, ModernRecordPacket<?>> builder) {
        // Intent
        builder.clientHandler(C2SIntentionPacket.class, this::handleClientIntent);

        // Status
        builder.clientHandler(C2SStatusRequestPacket.class, this::handleC2SStatusRequest);
        builder.clientHandler(C2SStatusPingRequestPacket.class, this::handleC2SStatusPingRequest);

        // Login
        builder.clientHandler(C2SHelloPacket.class, this::handleC2SHello);
        builder.serverHandler(HandshakePacket.class, this::handleS2CHandshake);
        builder.serverHandler(LoginPacket.class, this::handleS2CLogin);

        builder.clientHandler(C2SLoginAcknowledgedPacket.class, this::handleC2SLoginAcknowledged);
        builder.clientHandler(C2SFinishConfigurationPacket.class, this::handleC2SFinishConfiguration);

        builder.unhandledClient(this::passClientToNextPipeline);
        builder.unhandledServer(this::passServerToNextPipeline);
    }

    // Handshake
    public void handleClientIntent(final Connection connection, final C2SIntentionPacket packet) {
        switch (packet.intention()) {
            case LOGIN -> handleLogin(connection, packet);
            case STATUS -> connection.setState(PacketState.STATUS);
            case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
        }

        connection.setProtocolVersion(packet.protocolVersion());
    }

    // Status
    public void handleC2SStatusRequest(final Connection connection, final C2SStatusRequestPacket packet) {
        final BrodernProxy proxy = BrodernProxy.getProxy();
        final ServerStatus serverStatus = new ServerStatus(
                proxy.config().getMessage().append(String.format("\n(Connected To Server? %s)", connection.isConnectedToServer())),
                Optional.of(new ServerStatus.Players(proxy.config().getMaxPlayers(), 0, List.of())),
                Optional.of(new ServerStatus.Version(proxy.config().getBrand(), ModernRecordPacket.PROTOCOL_VERSION)),
                Optional.empty(),
                false
        );
        connection.sendToClient(new S2CStatusResponsePacket(serverStatus));
    }

    public void handleC2SStatusPingRequest(final Connection connection, final C2SStatusPingRequestPacket packet) {
        connection.sendToClient(new S2CStatusPongResponsePacket(packet.time()));
        connection.disconnect();
    }

    // Login
    public void handleLogin(final Connection connection, final C2SIntentionPacket packet) {
        connection.setState(PacketState.LOGIN);
        if (packet.protocolVersion() != ModernRecordPacket.PROTOCOL_VERSION) {
            connection.kick("Client is on " + packet.protocolVersion() + " while server is on " + ModernRecordPacket.PROTOCOL_VERSION);
        }
    }

    public void handleC2SHello(final Connection connection, final C2SHelloPacket packet) {
        connection.sendToServer(new HandshakePacket(packet.username()));

        final GameProfile profile = new GameProfile(packet.profileId(), packet.username(), new HashMap<>());
        connection.setProfile(profile);
    }

    public void handleS2CHandshake(final Connection connection, final HandshakePacket packet) {
        if (packet.username().equals("-")) {
            connection.sendToServer(new LoginPacket(BetaRecordPacket.PROTOCOL_VERSION, connection.getProfile().name()));
            connection.sendToClient(new S2CLoginFinishedPacket(connection.getProfile()));
        } else {
            connection.kick("Online mode isn't supported!");
        }
    }

    // Configuration
    public void handleC2SLoginAcknowledged(final Connection connection, final C2SLoginAcknowledgedPacket packet) {
        connection.setState(PacketState.CONFIGURATION);

        // Send Tags
        this.sendTags(connection);

        // Send Registries
        this.sendRegistries(connection);

        connection.sendToClient(S2CFinishConfigurationPacket.INSTANCE);
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

    public void handleC2SFinishConfiguration(final Connection connection, final C2SFinishConfigurationPacket packet) {
        connection.setState(PacketState.PLAY);
    }

    public void handleS2CLogin(final Connection connection, final LoginPacket packet) {
        // Do nothing currently
    }

    public void passClientToNextPipeline(final Connection connection, final ModernRecordPacket<?> packet) {}

    public void passServerToNextPipeline(final Connection connection, final BetaRecordPacket packet) {}

    public void handleS2CSetSpawnPos(final Connection connection, final SetSpawnPositionPacket packet) {
        this.spawnPos = packet.position();
    }
}
