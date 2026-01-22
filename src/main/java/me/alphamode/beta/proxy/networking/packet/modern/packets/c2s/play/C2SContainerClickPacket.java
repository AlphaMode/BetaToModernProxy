package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;

public class C2SContainerClickPacket implements C2SPlayPacket {
	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CONTAINER_CLICK;
	}
}
