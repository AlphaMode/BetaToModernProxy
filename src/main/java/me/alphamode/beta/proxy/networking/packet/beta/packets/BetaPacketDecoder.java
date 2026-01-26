package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.util.DebugUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

// TODO: Move to BetaPacketCodec
public final class BetaPacketDecoder extends ReplayingDecoder<Void> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketDecoder.class);
	public static final String KEY = "beta-packet-decoder";

	private final ClientConnection connection;

	public BetaPacketDecoder(final ClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		try {
			DebugUtil.printBuf(buf);
			final var packet = BetaPacketRegistry.INSTANCE.createPacket(buf.readUnsignedByte(), PacketDirection.SERVERBOUND, PacketState.PLAY, buf);
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Beta Packet {} received", packet);
			}

			out.add(packet);
		} catch (final Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to decode beta packet");
			}

			connection.kick(exception.getMessage());
		}
	}
}
