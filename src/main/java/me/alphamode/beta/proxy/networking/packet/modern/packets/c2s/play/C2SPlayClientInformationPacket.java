package me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.play;

import io.netty.buffer.ByteBuf;
import me.alphamode.beta.proxy.networking.packet.modern.enums.PacketState;
import me.alphamode.beta.proxy.networking.packet.modern.enums.serverbound.ServerboundPlayPackets;
import me.alphamode.beta.proxy.networking.packet.modern.packets.c2s.common.C2SCommonClientInformationPacket;
import me.alphamode.beta.proxy.util.codec.StreamCodec;
import me.alphamode.beta.proxy.util.data.modern.ClientInformation;

public record C2SPlayClientInformationPacket(
		ClientInformation information) implements C2SCommonClientInformationPacket<ServerboundPlayPackets> {
	public static final StreamCodec<ByteBuf, C2SPlayClientInformationPacket> CODEC = StreamCodec.composite(
			ClientInformation.CODEC,
			C2SPlayClientInformationPacket::information,
			C2SPlayClientInformationPacket::new
	);

	@Override
	public PacketState getState() {
		return PacketState.PLAY;
	}

	@Override
	public ServerboundPlayPackets getType() {
		return ServerboundPlayPackets.CLIENT_INFORMATION;
	}

	@Override
	public ClientInformation getInformation() {
		return this.information;
	}
}
