package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.configuration;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundConfigurationPackets;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ClientInformation;

public record C2SClientInformationPacket(ClientInformation information) implements C2SConfigurationPacket {
	public static final StreamCodec<ByteBuf, C2SClientInformationPacket> CODEC = StreamCodec.composite(
			ClientInformation.CODEC,
			C2SClientInformationPacket::information,
			C2SClientInformationPacket::new
	);

	@Override
	public ServerboundConfigurationPackets getType() {
		return ServerboundConfigurationPackets.CLIENT_INFORMATION;
	}
}
