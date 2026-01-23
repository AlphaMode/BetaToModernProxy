package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.codec.BasicStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2ConfigurationKeepAlivePacket(long time) implements S2CCommonKeepAlivePacket<ClientboundConfigurationPackets> {
	public static final StreamCodec<ByteBuf, S2ConfigurationKeepAlivePacket> CODEC = StreamCodec.composite(
			BasicStreamCodecs.LONG,
			S2ConfigurationKeepAlivePacket::time,
			S2ConfigurationKeepAlivePacket::new
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
	public long getTime() {
		return this.time;
	}
}
