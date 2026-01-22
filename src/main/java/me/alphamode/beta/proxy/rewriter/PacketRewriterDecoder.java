package me.alphamode.beta.proxy.rewriter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import me.alphamode.beta.proxy.networking.C2PChannel;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPacketRegistry;
import me.alphamode.beta.proxy.networking.packet.modern.ModernPackets;
import me.alphamode.beta.proxy.networking.packet.modern.ModernRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionRecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.status.C2SStatusRequestPacket;

import java.util.List;

// Client -> Proxy
public final class PacketRewriterDecoder extends MessageToMessageDecoder<ModernRecordPacket<ModernPackets>> {
	private final C2PChannel clientChannel;

	public PacketRewriterDecoder(final C2PChannel clientChannel) {
		this.clientChannel = clientChannel;
	}

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
					case TRANSFER -> throw new RuntimeException("transfer not supported");
				}
			} else if ((Object) packet instanceof C2SStatusRequestPacket) {
				// TODO
				/*IO.println("Sending Status Response");

				final String motd = "meowmeow§0§10";
				final ByteBuf buf = Unpooled.buffer();
				buf.writeByte(254);
				DisconnectPacket.CODEC.encode(buf, new DisconnectPacket(motd));

				this.clientChannel.getChannel().writeAndFlush(buf);*/
			}
		}
	}
}
