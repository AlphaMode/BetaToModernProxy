package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.packets.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.KeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SHelloPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.login.C2SLoginAcknowledgedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.login.S2CLoginFinishedPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;
import me.alphamode.beta.proxy.util.data.modern.GameProfile;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<ModernRecordPacket<ModernPackets>> {
	private final String realServerIp;

	public PacketRewriterDecoder(final String realServerIp) {
		this.realServerIp = realServerIp;
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
			if ((Object) packet instanceof C2SIntentionRecordPacket intentionPacket) {
				switch (intentionPacket.intention()) {
					case LOGIN -> connection.setState(PacketState.LOGIN);
					case STATUS -> connection.setState(PacketState.STATUS);
				}

				out.add(new HandshakePacket("-"));
				return;
			} else if ((Object) packet instanceof C2SStatusRequestPacket) {
				connection.send(new S2CStatusResponsePacket("{\"description\":{\"text\":\"Beta 1.7.3 Server (" + this.realServerIp + ")\"},\"players\":{\"online\":0,\"max\":20},\"version\":{\"name\":\"1.21.11\",\"protocol\":774}}"));
				connection.disconnect();
				return;
			} else if ((Object) packet instanceof C2SHelloPacket(String username, UUID profileId)) {
				out.add(new LoginPacket(username, 14));
				connection.send(new S2CLoginFinishedPacket(new GameProfile(profileId, username, new HashMap<>())));
				return;
			} else if (packet instanceof C2SKeepAlivePacket<?>) {
				out.add(new KeepAlivePacket());
				return;
			} else if ((Object) packet instanceof C2SLoginAcknowledgedPacket) {
				connection.setState(PacketState.CONFIGURATION);
				return;
			}

			out.add(packet);
		}
	}
}
