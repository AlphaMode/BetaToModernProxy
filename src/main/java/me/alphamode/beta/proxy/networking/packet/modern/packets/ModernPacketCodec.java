package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.NettyByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import net.raphimc.netminecraft.packet.PacketTypes;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ModernPacketCodec extends ByteToMessageCodec<ModernPacket<ModernPackets>> {
	private static final Logger LOGGER = LogManager.getLogger(ModernPacketCodec.class);
	public static final String KEY = "modern-packet-codec";

	private final ClientConnection connection;

	public ModernPacketCodec(final ClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void encode(final ChannelHandlerContext context, final ModernPacket<ModernPackets> packet, final ByteBuf buf) throws Exception {
		final ModernPackets type = packet.getType();
		ModernStreamCodecs.VAR_INT.encode((ByteStream) buf, type.getId());
		try {
			ModernPacketRegistry.INSTANCE.getCodec(type).encode((ByteStream) buf, packet);
		} catch (Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to encode modern packet");
				exception.printStackTrace();
			}

			connection.kick(exception.getMessage());
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		final int packetId = PacketTypes.readVarInt(buf);
		final ByteBuf packetData = buf.readBytes(buf.readableBytes());
		try {
			out.add(ModernPacketRegistry.INSTANCE.createPacket(packetId, PacketDirection.SERVERBOUND, connection.getState(), NettyByteStream.of(packetData)));
		} catch (final Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to decode modern packet with id {} in state {}", packetId, connection.getState());
				exception.printStackTrace();
			}

			connection.kick(exception.getMessage());
			throw new RuntimeException(exception);
		} finally {
			packetData.release();
		}
	}
}
