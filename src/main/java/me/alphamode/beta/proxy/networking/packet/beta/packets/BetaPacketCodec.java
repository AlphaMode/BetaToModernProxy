package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import io.netty.handler.codec.DecoderException;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.util.DebugUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public final class BetaPacketCodec extends ByteToMessageCodec<BetaPacket> {
	private static final Logger LOGGER = LogManager.getLogger(BetaPacketCodec.class);
	public static final String KEY = "beta-packet-codec";

	private final ClientConnection connection;

	public BetaPacketCodec(final ClientConnection connection) {
		this.connection = connection;
	}

	@Override
	protected void encode(final ChannelHandlerContext context, final BetaPacket packet, final ByteBuf buf) throws Exception {
		final BetaPackets type = packet.getType();
		buf.writeByte(type.getId());
		try {
			BetaPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			LOGGER.info("Failed to encode beta packet");
			throw new RuntimeException(exception);
		}
	}

	// TODO: Split Packets?
	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		try {
			while (buf.isReadable()) {
				final int checkpoint = buf.readerIndex();
				try {
					DebugUtil.printBuf(buf);
					final var packet = BetaPacketRegistry.INSTANCE.createPacket(buf.readUnsignedByte(), PacketDirection.SERVERBOUND, PacketState.PLAY, buf);
					if (BrodernProxy.getProxy().isDebug()) {
						LOGGER.info("Beta Packet {} received", packet);
					}

					out.add(packet);
				} catch (final DecoderException exception) {
					if (checkpoint >= 0) {
						buf.readerIndex(checkpoint);
					}

					break;
				}
			}
		} catch (final Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to decode beta packet");
			}

			connection.kick(exception.getMessage());
		}
	}
}
