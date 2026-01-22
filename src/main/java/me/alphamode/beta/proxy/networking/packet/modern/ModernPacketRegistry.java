package me.alphamode.beta.proxy.networking.packet.modern;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.PacketRegistry;
import me.alphamode.beta.proxy.networking.packet.RecordPacket;

public class ModernPacketRegistry extends PacketRegistry<ModernPackets> {
	public static final ModernPacketRegistry INSTANCE = new ModernPacketRegistry();
	private PacketState state = PacketState.HANDSHAKING;

	public ModernPacketRegistry() {
		this.registerVanillaPackets();
	}

	@Override
	public RecordPacket<ModernPackets> createPacket(final int id, final ByteBuf byteBuf) {
		final ModernPackets packetType = ModernClientboundPackets.getPacket(id, this.state);
		if (packetType == null) {
			throw new RuntimeException("Packet ? is not registered in the packet registry");
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
