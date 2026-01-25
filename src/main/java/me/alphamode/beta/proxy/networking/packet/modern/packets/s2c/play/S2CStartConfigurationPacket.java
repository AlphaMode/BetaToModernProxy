package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundPlayPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public class S2CStartConfigurationPacket implements S2CPlayPacket {
	public static final S2CStartConfigurationPacket INSTANCE = new S2CStartConfigurationPacket();
	public static StreamCodec<ByteBuf, S2CStartConfigurationPacket> CODEC = StreamCodec.unit(INSTANCE);

	private S2CStartConfigurationPacket() {
	}

	@Override
	public ClientboundPlayPackets getType() {
		return ClientboundPlayPackets.START_CONFIGURATION;
	}
}
