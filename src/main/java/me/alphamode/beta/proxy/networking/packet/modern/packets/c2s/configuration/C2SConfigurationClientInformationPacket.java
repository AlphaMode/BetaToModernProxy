package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonClientInformationPacket;
import me.alphamode.beta.proxy.util.ByteStream;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ClientInformation;

public record C2SConfigurationClientInformationPacket(
		ClientInformation information) implements C2SCommonClientInformationPacket<ServerboundConfigurationPackets> {
	public static final StreamCodec<ByteStream, C2SConfigurationClientInformationPacket> CODEC = StreamCodec.composite(
			ClientInformation.CODEC,
			C2SConfigurationClientInformationPacket::information,
			C2SConfigurationClientInformationPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.CONFIGURATION;
	}

	@Override
	public ServerboundConfigurationPackets getType() {
		return ServerboundConfigurationPackets.CLIENT_INFORMATION;
	}

	@Override
	public ClientInformation getInformation() {
		return this.information;
	}
}
