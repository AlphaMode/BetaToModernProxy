package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;

public class C2SPickItemFromEntityPacket implements C2SPlayPacket {
	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.PICK_ITEM_FROM_ENTITY;
	}
}
