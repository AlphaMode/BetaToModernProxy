package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CConfigurationKeepAlivePacket(
		long time) implements S2CCommonKeepAlivePacket<ClientboundConfigurationPackets> {
	public static final StreamCodec<ByteBuf, S2CConfigurationKeepAlivePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			S2CConfigurationKeepAlivePacket::time,
			S2CConfigurationKeepAlivePacket::new
	);

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.KEEP_ALIVE;
	}

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public long getId() {
		return this.time;
	}
}
