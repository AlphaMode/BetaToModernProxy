package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonKeepAlivePacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.CommonStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;

public record S2CConfigurationKeepAlivePacket(
		long id) implements S2CCommonKeepAlivePacket<ClientboundConfigurationPackets> {
	public static final StreamCodec<ByteStream, S2CConfigurationKeepAlivePacket> CODEC = StreamCodec.composite(
			CommonStreamCodecs.LONG,
			S2CConfigurationKeepAlivePacket::id,
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
		return this.id;
	}
}
