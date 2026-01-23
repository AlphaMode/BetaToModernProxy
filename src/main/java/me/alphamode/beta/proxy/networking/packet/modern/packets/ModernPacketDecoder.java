package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.util.List;

public class ModernPacketDecoder extends ByteToMessageDecoder {
	public static final String KEY = "modern-decoder";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		final ModernPacketRegistry packetRegistry = context.channel().attr(ProxyChannel.MODERN_PACKET_REGISTRY_KEY).get();
		if (packetRegistry == null) {
			throw new RuntimeException("Cannot decode modern packet as packet-registry is null!");
		} else {
			final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
			final int packetId = PacketTypes.readVarInt(buf);
			final ByteBuf packetData = buf.readBytes(buf.readableBytes());
			try {
				out.add(packetRegistry.createPacket(packetId, PacketDirection.SERVERBOUND, connection.getState(), packetData));
			} catch (Exception exception) {
				BrodernProxy.LOGGER.info("Failed to decode modern packet in state {}", connection.getState());
				throw new RuntimeException(exception);
			}
		}
	}
}
