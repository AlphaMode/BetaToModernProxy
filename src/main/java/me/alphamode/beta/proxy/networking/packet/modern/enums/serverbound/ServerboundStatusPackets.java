package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import net.lenni0451.mcstructs.core.Identifier;

public enum ServerboundStatusPackets implements ModernServerboundPackets {
	STATUS_REQUEST(0x00, Identifier.defaultNamespace("status_request"), PacketState.STATUS),
	PING_REQUEST(0x01, Identifier.defaultNamespace("ping_request"), PacketState.STATUS);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ServerboundStatusPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ServerboundStatusPackets getPacket(final int id) {
		for (final ServerboundStatusPackets packet : ServerboundStatusPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
