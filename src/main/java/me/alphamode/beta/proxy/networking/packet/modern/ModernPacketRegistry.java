package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;

public class ModernPacketRegistry extends PacketRegistry<ModernPackets> {
	private PacketState state = PacketState.HANDSHAKING;

	public ModernPacketRegistry() {
		this.registerVanillaPackets();
	}

	@Override
	public RecordPacket<ModernPackets> createPacket(final ModernPackets packetType, final ByteBuf byteBuf) {
		if (packetType == null) {
			throw new IllegalArgumentException("Packet ? is not registered in the packet registry");
		} else if (packetType.getState() != this.state) {
			throw new RuntimeException("Cannot create packet for different state!");
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
	}
}
