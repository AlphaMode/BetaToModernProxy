package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.alphamode.beta.proxy.Asterial;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.NettyByteStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class BetaPacketWriter extends MessageToByteEncoder<BetaPacket> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketWriter.class);
	public static final String KEY = "beta-packet-writer";

	private final ClientConnection connection;

	public BetaPacketWriter(final ClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void encode(final ChannelHandlerContext context, final BetaPacket packet, final ByteBuf buf) throws Exception {
		final BetaPacketType type = packet.getType();
		final ByteStream stream = NettyByteStream.of(buf);
		stream.writeByte((byte) type.getId());
		try {
			type.codec().encode(stream, packet);
		} catch (Exception exception) {
			if (Asterial.getProxy().isDebug()) {
				LOGGER.info("Failed to encode beta packet: {}", exception.getMessage());
			}

			this.connection.kick("Failed to encode beta packet: " + exception.getMessage());
		}
	}
}
