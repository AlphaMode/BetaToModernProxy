package me.alphamode.beta.proxy.networking.packet.beta.packets;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;

public class BetaPacketRegistry extends PacketRegistry<BetaPackets, BetaPacket> {
	public static final BetaPacketRegistry INSTANCE = new BetaPacketRegistry();

	@Override
	public BetaPacket createPacket(final int packetId, final PacketDirection direction, final PacketState state, final ByteBuf byteBuf) {
		final BetaPackets packetType = BetaPackets.getPacket(packetId);
		if (packetType == null) {
			throw new IllegalArgumentException("Could not determine beta packet type for id " + packetId);
		} else {
			return packetType.codec().decode(byteBuf);
		}
	}
}
