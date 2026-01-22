package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundHandshakingPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.handshaking.C2SIntentionPacket;

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
			return this.getCodec(packetType).decode(byteBuf);
		}
	}

	public PacketState getState() {
		return state;
	}

	public void setState(final PacketState state) {
		this.state = state;
	}

	private void registerVanillaPackets() {
		registerPacket(ServerboundHandshakingPackets.INTENTION, C2SIntentionPacket.CODEC);
	}
}
