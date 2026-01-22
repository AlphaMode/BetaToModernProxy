package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import me.alphamode.beta.proxy.util.data.Identifier;

public enum ClientboundPlayPackets implements ModernClientboundPackets {
	;

	@Override
	public Identifier getIdentifier() {
		return null;
	}

	@Override
	public PacketState getState() {
		return null;
	}

	@Override
	public int getId() {
		return 0;
	}
}
