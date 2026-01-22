package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play.C2SAcceptTeleportationPacket;

public class ModernPacketRegistry extends PacketRegistry<ModernPackets> {
	public static final ModernPacketRegistry INSTANCE = new ModernPacketRegistry();
	private PacketState state = PacketState.HANDSHAKING;

	public ModernPacketRegistry() {
		this.registerVanillaPackets();
	}

	@Override
	public RecordPacket<ModernPackets> createPacket(final int packetId, final PacketDirection direction, final ByteBuf byteBuf) {
		final ModernPackets packetType = ModernPackets.getPacket(packetId, direction, this.state);
		if (packetType == null) {
			throw new RuntimeException("Packet " + packetId + " is not registered in the packet registry");
		} else {
			return getCodec(packetType).decode(byteBuf);
		}
	}

	public PacketState getState() {
		return state;
	}

	public void setState(final PacketState state) {
		this.state = state;
	}

	private void registerVanillaPackets() {
        this.registerHandshakingPackets();
		this.registerPlayPackets();
		this.registerStatusPackets();
        this.registerLoginPackets();
        this.registerConfigurationPackets();
	}

    private void registerHandshakingPackets() {
        this.registerPacket(ServerboundHandshakingPackets.INTENTION, C2SIntentionPacket.CODEC);
    }

    private void registerPlayPackets() {
        this.registerPacket(ServerboundPlayPackets.ACCEPT_TELEPORTATION, C2SAcceptTeleportationPacket.CODEC);
    }

    private void registerStatusPackets() {

    }

    private void registerLoginPackets() {
    }

    private void registerConfigurationPackets() {

    }
}
