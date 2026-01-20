package me.alphamode.beta.proxy.networking.packet.beta;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.raphimc.netminecraft.constants.MCPipeline;
import net.raphimc.netminecraft.packet.Packet;
import net.raphimc.netminecraft.packet.registry.PacketRegistry;

import java.util.List;

// TODO: Use ReplayingDecoder? (RK says to use it)
public final class BetaPacketDecoder extends ByteToMessageDecoder {
	public static final String KEY = "beta-decoder";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) {
		final PacketRegistry packetRegistry = context.channel().attr(MCPipeline.PACKET_REGISTRY_ATTRIBUTE_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode beta packet as packet-registry is null!");
		} else {
			while (buf.readableBytes() > 0) {
				buf.markReaderIndex();

				final int packetId = buf.readUnsignedByte();
				final Packet packet = packetRegistry.createPacket(packetId, buf);

				out.add(packet);
			}
		}
	}
}
