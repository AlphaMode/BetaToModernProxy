package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCommonUpdateTagsPacket;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.TagNetworkSerialization;
import me.alphamode.beta.proxy.util.data.modern.registry.Registry;
import me.alphamode.beta.proxy.util.data.modern.registry.ResourceKey;

import java.util.Map;

public record S2CUpdateTagsPacket(
		Map<ResourceKey<? extends Registry<?>>, TagNetworkSerialization.NetworkPayload> tags) implements S2CCommonUpdateTagsPacket<ClientboundConfigurationPackets> {
	public static final StreamCodec<ByteBuf, S2CUpdateTagsPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.javaMap(ResourceKey.CODEC, TagNetworkSerialization.NetworkPayload.CODEC),
			S2CUpdateTagsPacket::tags,
			S2CUpdateTagsPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.UPDATE_TAGS;
	}
}
