package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.alphamode.beta.proxy.networking.Connection;
import me.alphamode.beta.proxy.networking.ProxyChannel;
import net.raphimc.netminecraft.packet.PacketTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ModernPacketReader extends ByteToMessageDecoder {
	private static final Logger LOGGER = LogManager.getLogger(ModernPacketReader.class);
	public static final String KEY = "modern-encoder";

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		final Connection connection = context.channel().attr(ProxyChannel.CONNECTION_KEY).get();
		final int packetId = PacketTypes.readVarInt(buf);
		final ByteBuf packetData = buf.readBytes(buf.readableBytes());
		try {
			out.add(ModernPacketRegistry.INSTANCE.createPacket(packetId, PacketDirection.SERVERBOUND, connection.getState(), packetData));
		} catch (Exception exception) {
			LOGGER.info("Failed to encode modern packet in state {}", connection.getState());
			throw new RuntimeException(exception);
		}
	}
}
