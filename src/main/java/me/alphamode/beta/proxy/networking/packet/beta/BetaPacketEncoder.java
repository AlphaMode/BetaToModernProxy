package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.Proxy;
import me.alphamode.beta.proxy.networking.packet.beta.packets.DisconnectPacket;
import me.alphamode.beta.proxy.networking.packet.beta.packets.RecordPacket;

public final class BetaPacketEncoder extends MessageToByteEncoder<RecordPacket> {
	public static final String KEY = "beta-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final RecordPacket packet, final ByteBuf buf) {
		final BetaPacketRegistry packetRegistry = context.channel().attr(Proxy.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
		if (packetRegistry == null) {
			throw new IllegalStateException("Can't write Packet without a packet registry");
		} else {
			final BetaPackets type = packet.getType();
			IO.println("Encoding packet: " + type + " " + packet);
			buf.writeByte(type.getId());
			try {
				packetRegistry.getCodec(type).encode(buf, packet);
			} catch (Exception exception) {
				final ByteBuf writeBuf = Unpooled.buffer();
				final DisconnectPacket disconnectPacket = new DisconnectPacket(exception.getMessage());
				try {
					packetRegistry.getCodec(BetaPackets.DISCONNECT).encode(writeBuf, disconnectPacket);
				} catch (Exception ignored) {
				}

				context.channel().writeAndFlush(disconnectPacket);
				context.channel().close();
			}
		}
	}
}
