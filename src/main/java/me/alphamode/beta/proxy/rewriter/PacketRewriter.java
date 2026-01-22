package me.alphamode.beta.proxy.rewriter;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.beta.packets.HandshakePacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.LoginPacket;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;

import java.util.List;

public final class PacketRewriter extends MessageToMessageCodec<ModernRecordPacket<ModernPackets>, ByteBuf> {
	// C -> P
	@Override
	protected void encode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {

		IO.println("encoding");
		IO.println(buf);
		out.add(buf);
		// TODO
	}

	// P -> C
	@Override
	protected void decode(final ChannelHandlerContext context, final ModernRecordPacket<ModernPackets> packet, final List<Object> out) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode modern packet as packet-registry is null!");
		} else {
			if ((Object) packet instanceof C2SIntentionRecordPacket intentionPacket) {
				switch (intentionPacket.intention()) {
					case LOGIN -> packetRegistry.setState(PacketState.LOGIN);
					case STATUS -> {
						packetRegistry.setState(PacketState.STATUS);
						// TODO: Disconnect
						return;
					}
					case TRANSFER -> throw new RuntimeException("transfer not supported");
				}

				out.add(new HandshakePacket("-"));
				return;
			}

			IO.println("decoding");
			IO.println(packet);
			out.add(packet);
			// TODO
		}
	}
}
