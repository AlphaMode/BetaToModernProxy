package me.alphamode.beta.proxy.rewriter;

import me.alphamode.beta.proxy.networking.packet.beta.BetaRecordPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketDirection;
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
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play.S2CDisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;
import net.lenni0451.mcstructs.text.TextComponent;

import java.util.HashMap;

public final class DecoderRewriter extends Rewriter {
	private final String realServerIp;

	public DecoderRewriter(final String realServerIp) {
		this.realServerIp = realServerIp; // TODO: find better way to handle data like this
	}

	@Override
	public void registerPackets() {
		this.registerRewriter(C2SIntentionRecordPacket.class, PacketDirection.SERVERBOUND, (connection, packet) -> {
			switch (packet.intention()) {
				case LOGIN -> connection.setState(PacketState.LOGIN);
				case STATUS -> connection.setState(PacketState.STATUS);
				case TRANSFER -> throw new RuntimeException("Transfer is unsupported");
			}

			return new HandshakePacket("-");
		});

		this.registerRewriter(C2SStatusRequestPacket.class, PacketDirection.SERVERBOUND, (connection, _) -> {
			connection.send(new S2CStatusResponsePacket("{\"description\":{\"text\":\"Beta 1.7.3 Server (" + this.realServerIp + ")\"},\"players\":{\"online\":0,\"max\":20},\"version\":{\"name\":\"1.21.11\",\"protocol\":774}}"));
			connection.disconnect();
			return null;
		});

		this.registerRewriter(C2SHelloPacket.class, PacketDirection.SERVERBOUND, (connection, packet) -> {
			connection.setUsername(packet.username());
			connection.setId(packet.profileId());
			connection.send(new S2CLoginFinishedPacket(new GameProfile(packet.profileId(), packet.username(), new HashMap<>())));
			return new LoginPacket(packet.username(), BetaRecordPacket.PROTOCOL_VERSION);
		});

		this.registerRewriter(C2SCommonKeepAlivePacket.class, PacketDirection.SERVERBOUND, (_, _) -> new KeepAlivePacket());

		this.registerRewriter(C2SLoginAcknowledgedPacket.class, PacketDirection.SERVERBOUND, (connection, _) -> {
			connection.setState(PacketState.CONFIGURATION);
			// send registries/etc
			connection.send(S2CFinishConfigurationPacket.INSTANCE);
			return null;
		});

		this.registerRewriter(C2SFinishConfigurationPacket.class, PacketDirection.SERVERBOUND, (connection, _) -> {
			connection.setState(PacketState.PLAY);
			connection.send(new S2CDisconnectPacket(TextComponent.of("sorry")));
			return null;
		});

		// Cancel
		this.registerRewriter(C2SCommonCustomPayloadPacket.class, PacketDirection.SERVERBOUND, (_, _) -> null);
		this.registerRewriter(C2SCustomQueryAnswerPacket.class, PacketDirection.SERVERBOUND, (_, _) -> null);
		this.registerRewriter(C2SClientInformationPacket.class, PacketDirection.SERVERBOUND, (_, _) -> null);
	}
}
