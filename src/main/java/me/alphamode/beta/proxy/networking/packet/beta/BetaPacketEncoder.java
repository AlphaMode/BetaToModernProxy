package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.packet.Packet;

public final class BetaPacketEncoder extends MessageToByteEncoder<Packet> {
	public static final String KEY = "beta-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final Packet packet, final ByteBuf buf) {
		final BetaPacketRegistry packetRegistry = (BetaPacketRegistry) context.channel().attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
		if (packetRegistry == null) {
			throw new IllegalStateException("Can't write Packet without a packet registry");
		} else {
			buf.writeByte(packetRegistry.getPacketId(packet));
			packet.write(buf, packetRegistry.getProtocolVersion());
		}
	}
}
