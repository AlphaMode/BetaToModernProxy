package me.alphamode.beta.proxy.rewriter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.packets.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.status.S2CStatusResponsePacket;

import java.util.List;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<ModernRecordPacket<ModernPackets>> {
	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final List<Object> out) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode modern packet as packet-registry is null!");
		} else {
			IO.println("decoding modern packet");
			IO.println(packet);
			if ((Object) packet instanceof C2SIntentionRecordPacket intentionPacket) {
				switch (intentionPacket.intention()) {
					case LOGIN -> packetRegistry.setState(PacketState.LOGIN);
					case STATUS -> packetRegistry.setState(PacketState.STATUS);
				}
			} else if ((Object) packet instanceof C2SStatusRequestPacket) {
				IO.println("Sending Status Response");

				final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
				if (connection != null && connection.isConnected()) {
					connection.send(new S2CStatusResponsePacket("{}"));
					connection.disconnect();
				}
			}
		}
	}
}
