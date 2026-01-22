package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonCustomPayloadPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SClientInformationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration.C2SFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SCustomQueryAnswerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration.S2CFinishConfigurationPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<ModernRecordPacket<ModernPackets>> {
	private final String realServerIp;

	private final Map<Class<? extends ModernRecordPacket<?>>, BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>> rewriters = new HashMap<>();

	public PacketRewriterDecoder(final String realServerIp) {
		this.realServerIp = realServerIp;

		this.registerRewriter(C2SIntentionRecordPacket.class, (connection, packet) -> {
			switch (packet.intention()) {
				case LOGIN -> connection.setState(PacketState.LOGIN);
				case STATUS -> connection.setState(PacketState.STATUS);
				case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
			}

			return new HandshakePacket("-");
		});

		this.registerRewriter(C2SStatusRequestPacket.class, (connection, _) -> {
			connection.send(new S2CStatusResponsePacket("{\"description\":{\"text\":\"Beta 1.7.3 Server (" + this.realServerIp + ")\"},\"players\":{\"online\":0,\"max\":20},\"version\":{\"name\":\"1.21.11\",\"protocol\":774}}"));
			connection.disconnect();
			return null;
		});

		this.registerRewriter(C2SHelloPacket.class, (connection, packet) -> {
			connection.setUsername(packet.username());
			connection.setId(packet.profileId());
			connection.send(new S2CLoginFinishedPacket(new GameProfile(packet.profileId(), packet.username(), new HashMap<>())));
			return new LoginPacket(packet.username(), BetaRecordPacket.PROTOCOL_VERSION);
		});

		this.registerRewriter(C2SCommonKeepAlivePacket.class, (_, _) -> new KeepAlivePacket());

		this.registerRewriter(C2SLoginAcknowledgedPacket.class, (connection, _) -> {
			connection.setState(PacketState.CONFIGURATION);
			// send registries/etc
			connection.send(S2CFinishConfigurationPacket.INSTANCE);
			return null;
		});

		this.registerRewriter(C2SFinishConfigurationPacket.class, (connection, _) -> {
			connection.setState(PacketState.PLAY);
			return null;
		});

		// Cancel
		this.registerRewriter(C2SCommonCustomPayloadPacket.class, (_, _) -> null);
		this.registerRewriter(C2SCustomQueryAnswerPacket.class, (_, _) -> null);
		this.registerRewriter(C2SClientInformationPacket.class, (_, _) -> null);
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final List<Object> out) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode modern packet as packet-registry is null!");
		} else {
			final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
			if (connection == null || !connection.isConnected()) {
				return;
			}

			IO.println(packet);
			for (final Class<?> clazz : this.rewriters.keySet()) {
				if (clazz.isAssignableFrom(packet.getClass())) {
					final BetaRecordPacket betaPacket = this.rewriters.get(clazz).apply(connection, packet);
					if (betaPacket != null) {
						out.add(betaPacket);
					}

					return;
				}
			}
		}
	}

	private <T extends ModernRecordPacket<?>> void registerRewriter(final Class<T> clazz, final BiFunction<Connection, T, BetaRecordPacket> wrapper) {
		this.rewriters.put(clazz, (BiFunction<Connection, ModernRecordPacket<?>, BetaRecordPacket>) wrapper);
	}
}
