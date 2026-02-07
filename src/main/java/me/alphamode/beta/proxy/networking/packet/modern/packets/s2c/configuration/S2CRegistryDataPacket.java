package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.RegistrySynchronization;
import me.alphamode.beta.proxy.util.data.modern.registry.Registry;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;

import java.util.List;

public record S2CRegistryDataPacket(
		ResourceKey<? extends Registry<?>> registry,
		List<RegistrySynchronization.PackedRegistryEntry> entries
) implements S2CConfigurationPacket {
	public static final StreamCodec<ByteStream, S2CRegistryDataPacket> CODEC = StreamCodec.composite(
			ResourceKey.CODEC,
			S2CRegistryDataPacket::registry,
			ModernStreamCodecs.list(RegistrySynchronization.PackedRegistryEntry.CODEC),
			S2CRegistryDataPacket::entries,
			S2CRegistryDataPacket::new
	);

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.REGISTRY_DATA;
	}
}
