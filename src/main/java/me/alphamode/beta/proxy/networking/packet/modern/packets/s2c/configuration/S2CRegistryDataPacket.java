package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.Registry;
import me.alphamode.beta.proxy.util.data.modern.RegistrySynchronization;
import me.alphamode.beta.proxy.util.data.modern.ResourceKey;

import java.util.List;

public record S2CRegistryDataPacket(
		ResourceKey<? extends Registry<?>> registry,
		List<RegistrySynchronization.PackedRegistryEntry> entries
) implements S2CConfigurationPacket {
	public static final StreamCodec<ByteBuf, S2CRegistryDataPacket> CODEC = StreamCodec.composite(

	);

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.REGISTRY_DATA;
	}
}
