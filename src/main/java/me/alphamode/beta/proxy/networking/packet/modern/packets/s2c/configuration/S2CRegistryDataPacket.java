package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.util.codec.ModernCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.RegistrySynchronization;
import me.alphamode.beta.proxy.util.data.modern.registry.Registry;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;

import java.util.List;

public record S2CRegistryDataPacket(
		ResourceKey<? extends Registry<?>> registry,
		List<RegistrySynchronization.PackedRegistryEntry> entries
) implements S2CConfigurationPacket {
	public static final StreamCodec<ByteBuf, ResourceKey<? extends Registry<?>>> REGISTRY_KEY_CODEC = new StreamCodec<>() {
		@Override
		public void encode(final ByteBuf buf, final ResourceKey<? extends Registry<?>> value) {
			ModernCodecs.IDENTIFIER.encode(buf, value.identifier());
		}

		@Override
		public ResourceKey<? extends Registry<?>> decode(final ByteBuf buf) {
			return ResourceKey.createRegistryKey(ModernCodecs.IDENTIFIER.decode(buf));
		}
	};

	public static final StreamCodec<ByteBuf, S2CRegistryDataPacket> CODEC = StreamCodec.composite(
			REGISTRY_KEY_CODEC,
			S2CRegistryDataPacket::registry,
			ModernCodecs.list(RegistrySynchronization.PackedRegistryEntry.CODEC),
			S2CRegistryDataPacket::entries,
			S2CRegistryDataPacket::new
	);

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.REGISTRY_DATA;
	}
}
