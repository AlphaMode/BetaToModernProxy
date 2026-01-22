package me.alphamode.beta.proxy.networking.packet.modern;

import me.alphamode.beta.proxy.networking.packet.Packets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public interface ModernPackets extends Packets {
	Identifier getIdentifier();

	PacketState getState();

	static <T extends ModernPackets> T getPacket(final int id, final PacketDirection direction, final PacketState state) {
		if (direction == PacketDirection.CLIENTBOUND) {
			return (T) ModernClientboundPackets.getPacket(id, state);
		} else {
			return (T) ModernServerboundPackets.getPacket(id, state);
		}
	}
}
