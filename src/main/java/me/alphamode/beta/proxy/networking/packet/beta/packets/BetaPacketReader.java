package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
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
		try {
			final var packet = BetaPacketRegistry.INSTANCE.createPacket(buf.readUnsignedByte(), PacketDirection.SERVERBOUND, PacketState.PLAY, buf);
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Beta Packet {} received", packet);
			}

			out.add(packet);
		} catch (final Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to decode beta packet");
			}

			connection.kick("Beta Exception: " + exception.getMessage());
		}
	}
}
