package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.packet.registry.PacketRegistry;

public final class BetaPacketEncoder extends MessageToByteEncoder<Packet> {
	public static final String KEY = "beta-encoder";

	@Override
	protected void encode(final ChannelHandlerContext context, final Packet packet, final ByteBuf buf) {
		final PacketRegistry packetRegistry = context.channel().attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
		if (packetRegistry == null) {
			throw new IllegalStateException("Can't write Packet without a packet registry");
		} else {
			final int packetId = packetRegistry.getPacketId(packet);
			buf.writeByte(packetId);
			packet.write(buf, packetRegistry.getProtocolVersion());

			final BetaPackets packetType = BetaPackets.getPacket(packetId);
			IO.println("Writing packet " + (packetType == null ? "Unknown" : packetType));
		}
	}
}
