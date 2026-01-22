package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class S2CFinishConfigurationPacket implements S2CConfigurationPacket {
	public static final S2CFinishConfigurationPacket INSTANCE = new S2CFinishConfigurationPacket();
	public static final StreamCodec<ByteBuf, S2CFinishConfigurationPacket> CODEC = StreamCodec.unit(INSTANCE);

	private S2CFinishConfigurationPacket() {
	}

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.FINISH_CONFIGURATION;
	}
}
