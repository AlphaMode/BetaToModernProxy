package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class C2SFinishConfigurationPacket implements C2SConfigurationPacket {
	public static final C2SFinishConfigurationPacket INSTANCE = new C2SFinishConfigurationPacket();
	public static final StreamCodec<ByteStream, C2SFinishConfigurationPacket> CODEC = StreamCodec.unit(INSTANCE);

	private C2SFinishConfigurationPacket() {
	}

	@Override
	public ServerboundConfigurationPackets getType() {
		return ServerboundConfigurationPackets.FINISH_CONFIGURATION;
	}
}
