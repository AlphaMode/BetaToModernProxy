package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;

public record C2SContainerClickPacket(int containerId, int stateId, short slot, byte button, ClickType clickType,
									  Object changedSlots, Object carriedItem) implements C2SPlayPacket {
	// VAR_INT
	// VAR_INT
	// SHORT
	// BYTE
	// ClickType
	// Slots
	// HashedStack

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CONTAINER_CLICK;
	}

	public enum ClickType {
		PICKUP,
		QUICK_MOVE,
		SWAP,
		CLONE,
		THROW,
		QUICK_CRAFT,
		PICKUP_ALL
	}
}
