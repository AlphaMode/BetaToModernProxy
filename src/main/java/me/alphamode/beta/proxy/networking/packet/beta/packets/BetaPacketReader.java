package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class BetaPacketReader extends ReplayingDecoder<Void> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketReader.class);
	public static final String KEY = "beta-packet-reader";

	private final ClientConnection connection;

	public BetaPacketReader(final ClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) {
		int id = -1;
		try {
			out.add(BetaPacketRegistry.INSTANCE.createPacket(id = buf.readUnsignedByte(), buf));
		} catch (final Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to decode beta packet with id {}", id);
			}

			this.connection.kick("Failed to decode beta packet with id " + id + ": " + exception.getMessage());
		}
	}
}
