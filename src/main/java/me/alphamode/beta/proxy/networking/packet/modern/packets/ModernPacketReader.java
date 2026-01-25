package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import me.alphamode.beta.proxy.networking.ClientConnection;
import net.raphimc.netminecraft.packet.PacketTypes;

import java.util.List;

public class ModernPacketReader extends ByteToMessageDecoder {
	public static final String KEY = "modern-encoder";

	private final ClientConnection connection;

	public ModernPacketReader(final ClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		final int packetId = PacketTypes.readVarInt(buf);
		final ByteBuf packetData = buf.readBytes(buf.readableBytes());
		try {
			out.add(ModernPacketRegistry.INSTANCE.createPacket(packetId, PacketDirection.SERVERBOUND, connection.getState(), packetData));
			packetData.release();
		} catch (final Exception exception) {
			packetData.release();
			connection.kick(exception.getMessage());
		}
	}
}
