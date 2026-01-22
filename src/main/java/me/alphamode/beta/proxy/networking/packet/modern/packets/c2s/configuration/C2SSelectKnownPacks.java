package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;

public class C2SSelectKnownPacks implements C2SConfigurationPacket {
	@Override
	public ServerboundConfigurationPackets getType() {
		return null;
	}
}
