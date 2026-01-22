package me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound;

import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.ModernServerboundPackets;
import net.lenni0451.mcstructs.core.Identifier;

public enum ServerboundHandshakingPackets implements ModernServerboundPackets {
	INTENTION(0x00, Identifier.defaultNamespace("intention"), PacketState.HANDSHAKING);

	private final int id;
	private final Identifier identifier;
	private final PacketState state;

	ServerboundHandshakingPackets(final int id, final Identifier identifier, final PacketState state) {
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

	public static ServerboundHandshakingPackets getPacket(final int id) {
		for (final ServerboundHandshakingPackets packet : ServerboundHandshakingPackets.values()) {
			if (packet.getId() == id) {
				return packet;
			}
		}

		return null;
	}
}
