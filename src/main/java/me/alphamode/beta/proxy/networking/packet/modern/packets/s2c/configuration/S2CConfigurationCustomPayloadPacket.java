package me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.clientbound.ClientboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.s2c.common.S2CCustomPayloadPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

public record S2CConfigurationCustomPayloadPacket(Identifier id,
												  byte[] data) implements S2CCustomPayloadPacket<ClientboundConfigurationPackets> {
	public static final StreamCodec<ByteStream, S2CConfigurationCustomPayloadPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.IDENTIFIER,
			S2CConfigurationCustomPayloadPacket::id,
			ModernStreamCodecs.BYTE_ARRAY,
			S2CConfigurationCustomPayloadPacket::data,
			S2CConfigurationCustomPayloadPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public ClientboundConfigurationPackets getType() {
		return ClientboundConfigurationPackets.CUSTOM_PAYLOAD;
	}
}
