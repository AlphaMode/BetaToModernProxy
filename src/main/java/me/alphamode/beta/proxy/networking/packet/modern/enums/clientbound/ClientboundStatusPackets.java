package me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound;

import me.alphamode.beta.proxy.networking.packet.modern.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernClientboundPackets;
import net.lenni0451.mcstructs.core.Identifier;

public enum ClientboundStatusPackets implements ModernClientboundPackets {
	STATUS_RESPONSE(0x00, Identifier.defaultNamespace("status_response"), PacketState.STATUS),
	PONG_RESPONSE(0x01, Identifier.defaultNamespace("pong_response"), PacketState.STATUS);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ClientboundStatusPackets(final int id, final Identifier identifier, final PacketState state) {
		this.id = id;
		this.identifier = identifier;
		this.state = state;
	}

	@Override
	public int getId() {
		return this.id;
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public PacketState getState() {
		return this.state;
	}

	public static ClientboundStatusPackets getPacket(final int id) {
		for (final ClientboundStatusPackets packet : ClientboundStatusPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
