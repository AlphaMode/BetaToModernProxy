package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonCustomPayloadPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.ModernStreamCodecs;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import net.lenni0451.mcstructs.core.Identifier;

public record C2SConfigurationCustomPayloadPacket(Identifier identifier,
												  byte[] data) implements C2SCommonCustomPayloadPacket<ServerboundConfigurationPackets> {
	public static final StreamCodec<ByteStream, C2SConfigurationCustomPayloadPacket> CODEC = StreamCodec.composite(
			ModernStreamCodecs.IDENTIFIER,
			C2SConfigurationCustomPayloadPacket::identifier,
			ModernStreamCodecs.BYTE_ARRAY,
			C2SConfigurationCustomPayloadPacket::data,
			C2SConfigurationCustomPayloadPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public ServerboundConfigurationPackets getType() {
		return ServerboundConfigurationPackets.CUSTOM_PAYLOAD;
	}

	@Override
	public Identifier getIdentifier() {
		return this.identifier;
	}

	@Override
	public byte[] getData() {
		return this.data;
	}
}
