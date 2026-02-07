package me.alphamode.beta.proxy.networking.packet.beta.packets;

import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.beta.enums.BetaPacketType;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketDirection;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.util.ByteStream;

public class BetaPacketRegistry extends PacketRegistry<BetaPacketType, BetaPacket> {
	public static final BetaPacketRegistry INSTANCE = new BetaPacketRegistry();

	@Override
	public BetaPacket createPacket(final int packetId, final PacketDirection direction, final PacketState state, final ByteStream buf) {
		final BetaPacketType packetType = BetaPacketType.byId(packetId);
		if (packetType == null) {
			throw new IllegalArgumentException("Could not determine beta packet type for id " + packetId);
		} else {
			return packetType.codec().decode(buf);
		}
	}
}
