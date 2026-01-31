package me.alphamode.beta.proxy.networking.packet.modern.packets;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import me.alphamode.beta.proxy.BrodernProxy;
import me.alphamode.beta.proxy.networking.ClientConnection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonKeepAlivePacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SClientTickEndPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SMovePlayerPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SPlayerInputPacket;
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
		ModernStreamCodecs.VAR_INT.encode(buf, type.getId());
		try {
			ModernPacketRegistry.INSTANCE.getCodec(type).encode(buf, packet);
		} catch (Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to encode modern packet");
			}

			exception.printStackTrace();
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected void decode(final ChannelHandlerContext context, final ByteBuf buf, final List<Object> out) throws Exception {
		final int packetId = PacketTypes.readVarInt(buf);
		final ByteBuf packetData = buf.readBytes(buf.readableBytes());
		try {
			final var packet = ModernPacketRegistry.INSTANCE.createPacket(packetId, PacketDirection.SERVERBOUND, connection.getState(), packetData);
			if (BrodernProxy.getProxy().isDebug() && !(
					packet instanceof C2SCommonKeepAlivePacket<?>
							|| (Object) packet instanceof C2SClientTickEndPacket
							|| (Object) packet instanceof C2SMovePlayerPacket.Pos
							|| (Object) packet instanceof C2SMovePlayerPacket.Rot
							|| (Object) packet instanceof C2SMovePlayerPacket.PosRot
							|| (Object) packet instanceof C2SMovePlayerPacket.StatusOnly
							|| (Object) packet instanceof C2SPlayerInputPacket
			)) {
				LOGGER.info("Modern Packet {} received", packet);
			}

			if (packet != null) {
				out.add(packet);
			} else {
				LOGGER.warn("Received null client packet!");
			}

			packetData.release();
		} catch (final Exception exception) {
			if (BrodernProxy.getProxy().isDebug()) {
				LOGGER.info("Failed to decode modern packet with id {} in state {}", packetId, connection.getState());
			}

			packetData.release();
			connection.kick(exception.getMessage());
			throw new RuntimeException(exception);
		}
	}
}
